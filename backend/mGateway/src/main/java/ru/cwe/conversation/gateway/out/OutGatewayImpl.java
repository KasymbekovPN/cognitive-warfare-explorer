package ru.cwe.conversation.gateway.out;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.container.MessageContainer;
import ru.cwe.conversation.decoder.MessageDecoder;
import ru.cwe.conversation.encoder.MessageEncoder;
import ru.cwe.conversation.gateway.BootstrapHolder;
import ru.cwe.conversation.gateway.FutureProcessor;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.processing.ClientMessageTransmitter;

@RequiredArgsConstructor
public final class OutGatewayImpl implements OutGateway {
	private final BootstrapHolder holder;
	private final FutureProcessor futureProcessor;
	private final MessageContainer<ConfirmationMessage> confirmationContainer;

	@Override
	public void send(final PayloadMessage message) {
		ChannelFutureSupplier supplier = new ChannelFutureSupplier() {
			@Override
			public ChannelFuture get() throws InterruptedException {
				return holder.getFuture();
			}
		};
		send(message, supplier);
	}

	@Override
	public void send(final PayloadMessage message, final String host, final int port) {
		ChannelFutureSupplier supplier = new ChannelFutureSupplier() {
			@Override
			public ChannelFuture get() throws InterruptedException {
				return holder.getFuture(host, port);
			}
		};
		send(message, supplier);
	}

	@Override
	public void shutdown() {
		holder.shutdown();
	}

	private void send(final PayloadMessage message, final ChannelFutureSupplier supplier){
		holder.getBootstrap().handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(
					MessageEncoder.instance(),
					MessageDecoder.instance(),
					ClientMessageTransmitter.instance(message, confirmationContainer)
				);
			}
		});

		try{
			futureProcessor.process(supplier.get());
		} catch (Throwable ignored){}
		finally {
			holder.shutdown();
		}
	}

	private interface ChannelFutureSupplier {
		ChannelFuture get() throws InterruptedException;
	}
}
