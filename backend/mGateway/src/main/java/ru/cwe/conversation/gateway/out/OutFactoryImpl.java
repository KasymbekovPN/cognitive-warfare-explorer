package ru.cwe.conversation.gateway.out;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import ru.cwe.conversation.container.MessageContainer;
import ru.cwe.conversation.gateway.FutureProcessorImpl;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;

public final class OutFactoryImpl implements OutFactory {
	@Override
	public OutGateway create(final MessageContainer<ConfirmationMessage> container,
							 final String host,
							 final int port) {
		Bootstrap bootstrap = new Bootstrap()
			.channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE, true);
		BootstrapHolderImpl holder = BootstrapHolderImpl.instance(bootstrap, host, port);

		return new OutGatewayImpl(holder, new FutureProcessorImpl(), container);
	}
}
