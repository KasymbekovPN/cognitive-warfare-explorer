package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;
import ru.cwe.conversation.message.MessageType;

public final class MessageTypeByteBufferValueWriter implements ByteBufferValueWriter<MessageType> {
	@Override
	public void write(ByteBuf buffer, MessageType element) {
		buffer.writeInt(element.getValue());
	}
}
