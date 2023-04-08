package ru.cwe.conversation.processing;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.cwe.conversation.container.MessageContainer;
import ru.cwe.conversation.converter.ToConfirmationMessageConverter;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.messagesource.MessageSource;

import java.util.Optional;
import java.util.function.Function;

public final class ClientMessageTransmitter extends ChannelInboundHandlerAdapter {
	private final MessageSource<PayloadMessage> messageSource;
	private final MessageContainer<ConfirmationMessage> messageContainer;
	private final Function<Object, Optional<ConfirmationMessage>> converter;

	public static Builder builder(){
		return new Builder();
	}

	public static ClientMessageTransmitter instance(MessageSource<PayloadMessage> source,
													MessageContainer<ConfirmationMessage> container){
		return builder().build(source, container);
	}

	private ClientMessageTransmitter(MessageSource<PayloadMessage> messageSource,
									 MessageContainer<ConfirmationMessage> messageContainer,
									 Function<Object, Optional<ConfirmationMessage>> converter) {
		this.messageSource = messageSource;
		this.messageContainer = messageContainer;
		this.converter = converter;
	}

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

	public static class Builder{
		private Function<Object, Optional<ConfirmationMessage>> converter;

		public Builder converter(Function<Object, Optional<ConfirmationMessage>> converter){
			this.converter = converter;
			return this;
		}

		public ClientMessageTransmitter build(MessageSource<PayloadMessage> source,
											  MessageContainer<ConfirmationMessage> container){
			return new ClientMessageTransmitter(
				source,
				container,
				converter != null ? converter : new ToConfirmationMessageConverter()
			);
		}
	}
}
