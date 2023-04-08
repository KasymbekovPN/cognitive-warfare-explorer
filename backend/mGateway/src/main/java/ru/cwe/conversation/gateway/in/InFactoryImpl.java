package ru.cwe.conversation.gateway.in;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import ru.cwe.conversation.container.MessageContainer;
import ru.cwe.conversation.decoder.MessageDecoder;
import ru.cwe.conversation.encoder.MessageEncoder;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.processing.ServerMessageReceiver;

public final class InFactoryImpl implements InFactory {

	@Override
	public InGateway create(MessageContainer<PayloadMessage> requestMessageContainer,
							MessageContainer<PayloadMessage> responseMessageContainer,
							int port) {

		MessageDecoder decoder = MessageDecoder.instance();
		MessageEncoder encoder = MessageEncoder.instance();
		ServerMessageReceiver receiver = ServerMessageReceiver.instance(requestMessageContainer, responseMessageContainer);

		ServerBootstrap bootstrap = new ServerBootstrap()
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(
						decoder,
						encoder,
						receiver
					);
				}
			})
			.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true);

		ServerHolderImpl holder = ServerHolderImpl.builder().build(bootstrap, port);

		return new InGatewayImpl(holder, new FutureProcessorImpl());
	}
}
