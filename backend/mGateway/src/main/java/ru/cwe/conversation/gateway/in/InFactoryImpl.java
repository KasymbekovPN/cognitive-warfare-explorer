package ru.cwe.conversation.gateway.in;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import ru.cwe.conversation.container.MessageContainer;
import ru.cwe.conversation.decoder.PayloadMessageDecoder;
import ru.cwe.conversation.encoder.MessageEncoder;
import ru.cwe.conversation.gateway.FutureProcessorImpl;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.processing.ServerMessageReceiver;

public final class InFactoryImpl implements InFactory {

	@Override
	public InGateway create(final MessageContainer<PayloadMessage> requestMessageContainer,
							final MessageContainer<PayloadMessage> responseMessageContainer,
							final int port) {

		ServerBootstrap bootstrap = new ServerBootstrap()
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(
						PayloadMessageDecoder.instance(),
						MessageEncoder.instance(),
						ServerMessageReceiver.instance(requestMessageContainer, responseMessageContainer)
					);
				}
			})
			.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true);

		ServerBootstrapHolderImpl holder = ServerBootstrapHolderImpl.builder().build(bootstrap, port);

		return new InGatewayImpl(holder, new FutureProcessorImpl());
	}
}
