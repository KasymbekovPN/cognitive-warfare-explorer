package ru.cwe.conversation.gateway.out;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import ru.cwe.conversation.container.MessageContainer;
import ru.cwe.conversation.gateway.FutureProcessorImpl;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;

public final class OutFactoryImpl implements OutFactory {
	@Override
	public OutGateway create(MessageContainer<ConfirmationMessage> container, String host, int port) {
		Bootstrap bootstrap = new Bootstrap()
			.channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE, true);
		OutBootstrapHolder holder = OutBootstrapHolder.instance(bootstrap, host, port);

		return new OutGatewayImpl(holder, new FutureProcessorImpl(), container);
	}
}
