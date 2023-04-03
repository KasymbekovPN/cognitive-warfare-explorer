package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;
import ru.cwe.conversation.message.Priorities;
import ru.cwe.conversation.message.Versions;

public final class ConfirmationHeaderByteBufferValueWriter implements ByteBufferValueWriter<Integer[]>{

	@Override
	public void write(ByteBuf buffer, Integer[] element) {
		int version = Versions.MAX & element[0];
		int priority = Priorities.MAX & element[1];
		int type = 0b11 & element[2];
		int result = 0b11 & element[3];

		buffer.writeChar(version + (priority << 10));
		buffer.writeChar(type + (result << 2));
	}
}
