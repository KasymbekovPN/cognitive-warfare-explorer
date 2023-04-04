package ru.cwe.conversation.processing;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.cwe.conversation.container.Container;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.payload.PayloadMessage;

import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
public final class ServerMessageReceiver extends ChannelInboundHandlerAdapter {
	private final Container<PayloadMessage> requestContainer;
	private final Container<PayloadMessage> responseContainer;
	private final Function<Object, Optional<PayloadMessage>> toPayloadMessageConverter;
	private final Function<PayloadMessage, ConfirmationMessage> toConfirmationMessageConverter;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Optional<PayloadMessage> result = toPayloadMessageConverter.apply(msg);
		if (result.isPresent()){
			PayloadMessage payloadMessage = result.get();
			Container<PayloadMessage> container = payloadMessage.getType().equals(MessageType.REQUEST)
				? requestContainer
				: responseContainer;
			container.append(payloadMessage);
			ChannelFuture future = ctx.writeAndFlush(toConfirmationMessageConverter.apply(payloadMessage));
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}
}
