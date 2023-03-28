package ru.cwe.conversation.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.writer.buffer.ByteBufferWriter;
import ru.cwe.conversation.message.Message;
import ru.cwe.conversation.message.MessageType;

@RequiredArgsConstructor
public final class MessageEncoder extends MessageToByteEncoder<Message> {
	private final ByteBufferWriter<Message> confirmationWriter;
	private final ByteBufferWriter<Message> payloadWriter;

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		ByteBufferWriter<Message> writer = msg.getType().equals(MessageType.CONFIRMATION)
			? confirmationWriter
			: payloadWriter;
		writer.write(out, msg);
	}
}
