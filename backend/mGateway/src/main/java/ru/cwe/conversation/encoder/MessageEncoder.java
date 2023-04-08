package ru.cwe.conversation.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.writer.buffer.ByteBufferWriter;
import ru.cwe.conversation.message.Message;
import ru.cwe.conversation.message.MessageType;

@RequiredArgsConstructor
public final class MessageEncoder extends MessageToByteEncoder<Message> {
	private final ByteBufferWriter<ConfirmationMessage> confirmationWriter;
	private final ByteBufferWriter<PayloadMessage> payloadWriter;

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		if (msg.getType().equals(MessageType.CONFIRMATION)){
			confirmationWriter.write(out, (ConfirmationMessage) msg);
		} else {
			payloadWriter.write(out, (PayloadMessage) msg);
		}
	}
}
