package ru.cwe.conversation.gateway.out;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.cwe.conversation.container.MessageContainer;
import ru.cwe.conversation.decoder.ConfirmationMessageDecoder;
import ru.cwe.conversation.encoder.MessageEncoder;
import ru.cwe.conversation.gateway.FutureProcessor;
import ru.cwe.conversation.holder.SentMessageHolder;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.processing.ClientMessageTransmitter;

@RequiredArgsConstructor
public final class OutGatewayImpl implements OutGateway {
	private final BootstrapHolder bootstrapHolder;
	private final FutureProcessor futureProcessor;
	private final MessageContainer<ConfirmationMessage> confirmationContainer;

	@Setter
	private SentMessageHolder sentMessageHolder;

	@Override
	public void send(final PayloadMessage message) {
		ChannelFutureSupplier supplier = new ChannelFutureSupplier() {
			@Override
			public ChannelFuture get() throws InterruptedException {
				return bootstrapHolder.getFuture();
			}
		};
		send(message, supplier);
	}

	@Override
	public void send(final PayloadMessage message,
					 final String host,
					 final int port) {
		ChannelFutureSupplier supplier = new ChannelFutureSupplier() {
			@Override
			public ChannelFuture get() throws InterruptedException {
				return bootstrapHolder.getFuture(host, port);
			}
		};
		send(message, supplier);
	}

	@Override
	public void shutdown() {
		bootstrapHolder.shutdown();
	}

	private void send(final PayloadMessage message,
					  final ChannelFutureSupplier supplier){
		// TODO: 18.04.2023 restore
//		bootstrapHolder.getBootstrap().handler(new ChannelInitializer<SocketChannel>() {
//			@Override
//			protected void initChannel(SocketChannel ch) throws Exception {
//				ch.pipeline().addLast(
//					MessageEncoder.instance(),
//					ConfirmationMessageDecoder.instance(),
//					ClientMessageTransmitter.instance(message, confirmationContainer)
//				);
//			}
//		});
//
//		try{
//			ChannelFuture future = supplier.get();
//			futureProcessor.process(future);
//		} catch (Throwable ex){
//			ex.printStackTrace();
//		}
	}

	private interface ChannelFutureSupplier {
		ChannelFuture get() throws InterruptedException;
	}
}
