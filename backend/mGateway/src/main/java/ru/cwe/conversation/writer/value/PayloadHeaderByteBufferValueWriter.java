package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;
import ru.cwe.conversation.message.Priorities;
import ru.cwe.conversation.message.Versions;

public final class PayloadHeaderByteBufferValueWriter implements ByteBufferValueWriter<Integer[]>{

	@Override
	public void write(final ByteBuf buffer, final Integer[] element) {
		int version = Versions.MAX & element[0];
		int priority = Priorities.MAX & element[1];
		int type = 0b11 & element[2];

		buffer.writeChar(version + (priority << 10));
		buffer.writeChar(type);
	}
}
