package ru.cwe.conversation.processing;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.container.MessageContainer;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.messagesource.MessageSource;

import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public final class ClientMessageTransmitter extends ChannelInboundHandlerAdapter {
	private final MessageSource<PayloadMessage> messageSource;
	private final Function<Object, Optional<ConfirmationMessage>> converter;
	private final MessageContainer<ConfirmationMessage> messageContainer;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(messageSource.next());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Optional<ConfirmationMessage> result = converter.apply(msg);
		result.ifPresent(messageContainer::append);
		ctx.close();
	}
}
