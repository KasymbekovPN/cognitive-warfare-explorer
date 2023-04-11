package ru.cwe.conversation.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.writer.buffer.ByteBufferWriter;
import ru.cwe.conversation.message.Message;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.writer.buffer.ConfirmationByteBufferWriter;
import ru.cwe.conversation.writer.buffer.PayloadByteBufferWriter;

public final class MessageEncoder extends MessageToByteEncoder<Message> {
	private final ByteBufferWriter<ConfirmationMessage> confirmationWriter;
	private final ByteBufferWriter<PayloadMessage> payloadWriter;

	public static Builder builder(){
		return new Builder();
	}

	public static MessageEncoder instance(){
		return builder().build();
	}

	private MessageEncoder(final ByteBufferWriter<ConfirmationMessage> confirmationWriter,
						   final ByteBufferWriter<PayloadMessage> payloadWriter) {
		this.confirmationWriter = confirmationWriter;
		this.payloadWriter = payloadWriter;
	}

	@Override
	protected void encode(final ChannelHandlerContext ctx, final Message msg, final ByteBuf out) throws Exception {
		if (msg.getType().equals(MessageType.CONFIRMATION)){
			confirmationWriter.write(out, (ConfirmationMessage) msg);
		} else {
			payloadWriter.write(out, (PayloadMessage) msg);
		}
	}

	public static class Builder {
		private ByteBufferWriter<ConfirmationMessage> confirmationWriter;
		private ByteBufferWriter<PayloadMessage> payloadWriter;

		public Builder confirmation(final ByteBufferWriter<ConfirmationMessage> confirmationWriter){
			this.confirmationWriter = confirmationWriter;
			return this;
		}

		public Builder payload(final ByteBufferWriter<PayloadMessage> payloadWriter){
			this.payloadWriter = payloadWriter;
			return this;
		}

		public MessageEncoder build(){
			return new MessageEncoder(
				confirmationWriter != null ? confirmationWriter : ConfirmationByteBufferWriter.instance(),
				payloadWriter != null ? payloadWriter : PayloadByteBufferWriter.instance()
			);
		}
	}
}
