package ru.cwe.conversation.processing;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.cwe.conversation.converter.ToConfirmationMessageConverter;
import ru.cwe.conversation.holder.SentMessageHolder;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.payload.PayloadMessage;

import java.util.Optional;
import java.util.function.Function;

public final class ClientMessageTransmitter extends ChannelInboundHandlerAdapter {
	private final PayloadMessage message;
	private final SentMessageHolder sentMessageHolder;
	private final Function<Object, Optional<ConfirmationMessage>> converter;

	public static Builder builder(){
		return new Builder();
	}

	public static ClientMessageTransmitter instance(final PayloadMessage message,
													final SentMessageHolder sentMessageHolder){
		return builder().build(message, sentMessageHolder);
	}

	private ClientMessageTransmitter(final PayloadMessage message,
									 final SentMessageHolder sentMessageHolder,
									 final Function<Object, Optional<ConfirmationMessage>> converter) {
		this.message = message;
		this.sentMessageHolder = sentMessageHolder;
		this.converter = converter;
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(message);
	}

	@Override
	public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
		Optional<ConfirmationMessage> result = converter.apply(msg);
		result.ifPresent(m -> { sentMessageHolder.erase(m.getUuid()); });
		ctx.close();
	}

	public static class Builder {
		private Function<Object, Optional<ConfirmationMessage>> converter;

		public Builder converter(final Function<Object, Optional<ConfirmationMessage>> converter){
			this.converter = converter;
			return this;
		}

		public ClientMessageTransmitter build(final PayloadMessage message,
											  final SentMessageHolder sentMessageHolder){
			return new ClientMessageTransmitter(
				message,
				sentMessageHolder,
				converter != null ? converter : new ToConfirmationMessageConverter()
			);
		}
	}
}
