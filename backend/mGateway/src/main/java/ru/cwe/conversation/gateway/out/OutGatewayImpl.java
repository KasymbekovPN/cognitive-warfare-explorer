package ru.cwe.conversation.gateway.out;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.container.MessageContainer;
import ru.cwe.conversation.decoder.MessageDecoder;
import ru.cwe.conversation.encoder.MessageEncoder;
import ru.cwe.conversation.gateway.BootstrapHolder;
import ru.cwe.conversation.gateway.in.FutureProcessor;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.processing.ClientMessageTransmitter;

@RequiredArgsConstructor
public final class OutGatewayImpl implements OutGateway {
	private final BootstrapHolder bootstrapHolder;
	private final FutureProcessor futureProcessor;
	private final MessageContainer<ConfirmationMessage> confirmationContainer;

	@Override
	public void send(PayloadMessage message) {
		bootstrapHolder.getBootstrap().handler(new ChannelInitializer<SocketChannel>() {
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
			futureProcessor.process(bootstrapHolder.getFuture());
		} catch (Throwable ignored){}
		finally {
			bootstrapHolder.shutdown();
		}
	}

	@Override
	public void shutdown() {
		bootstrapHolder.shutdown();
	}
}
