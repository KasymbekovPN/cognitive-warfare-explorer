package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;
import ru.cwe.conversation.message.MessageType;

public final class MessageTypeByteBufferValueReader implements ByteBufferValueReader<MessageType> {

	@Override
	public MessageType read(ByteBuf buffer) {
		return MessageType.valueOf(buffer.readInt());
	}
}
