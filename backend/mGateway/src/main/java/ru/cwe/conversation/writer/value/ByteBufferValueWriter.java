package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;

public interface ByteBufferValueWriter<E> {
	void write(ByteBuf buffer, E element);
}
