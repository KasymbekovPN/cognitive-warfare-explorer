package ru.cwe.conversation.processing;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.cwe.conversation.container.MessageContainer;
import ru.cwe.conversation.converter.PayloadToConfirmationMessageConverter;
import ru.cwe.conversation.converter.ToPayloadMessageConverter;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.payload.PayloadMessage;

import java.util.Optional;
import java.util.function.Function;

public final class ServerMessageReceiver extends ChannelInboundHandlerAdapter {
	private final MessageContainer<PayloadMessage> requestMessageContainer;
	private final MessageContainer<PayloadMessage> responseMessageContainer;
	private final Function<Object, Optional<PayloadMessage>> toPayloadMessageConverter;
	private final Function<PayloadMessage, ConfirmationMessage> toConfirmationMessageConverter;

	public static Builder builder(){
		return new Builder();
	}

	public static ServerMessageReceiver instance(MessageContainer<PayloadMessage> requestContainer,
												 MessageContainer<PayloadMessage> responseContainer){
		return builder().build(requestContainer, responseContainer);
	}

	private ServerMessageReceiver(MessageContainer<PayloadMessage> requestMessageContainer,
								  MessageContainer<PayloadMessage> responseMessageContainer,
								  Function<Object, Optional<PayloadMessage>> toPayloadMessageConverter,
								  Function<PayloadMessage, ConfirmationMessage> toConfirmationMessageConverter) {
		this.requestMessageContainer = requestMessageContainer;
		this.responseMessageContainer = responseMessageContainer;
		this.toPayloadMessageConverter = toPayloadMessageConverter;
		this.toConfirmationMessageConverter = toConfirmationMessageConverter;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Optional<PayloadMessage> result = toPayloadMessageConverter.apply(msg);
		if (result.isPresent()){
			PayloadMessage payloadMessage = result.get();
			MessageContainer<PayloadMessage> messageContainer = payloadMessage.getType().equals(MessageType.REQUEST)
				? requestMessageContainer
				: responseMessageContainer;
			messageContainer.append(payloadMessage);
			ChannelFuture future = ctx.writeAndFlush(toConfirmationMessageConverter.apply(payloadMessage));
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	public static class Builder {
		private Function<Object, Optional<PayloadMessage>> toPayloadMessageConverter;
		private Function<PayloadMessage, ConfirmationMessage> toConfirmationMessageConverter;

		public Builder toPayloadConverter(Function<Object, Optional<PayloadMessage>> toPayloadMessageConverter){
			this.toPayloadMessageConverter = toPayloadMessageConverter;
			return this;
		}

		public Builder toConfirmationConverter(Function<PayloadMessage, ConfirmationMessage> toConfirmationMessageConverter){
			this.toConfirmationMessageConverter = toConfirmationMessageConverter;
			return this;
		}

		public ServerMessageReceiver build(MessageContainer<PayloadMessage> requestContainer,
										   MessageContainer<PayloadMessage> responseContainer){
			return new ServerMessageReceiver(
				requestContainer,
				responseContainer,
				toPayloadMessageConverter != null ? toPayloadMessageConverter : new ToPayloadMessageConverter(),
				toConfirmationMessageConverter != null ? toConfirmationMessageConverter : new PayloadToConfirmationMessageConverter()
			);
		}
	}
}
