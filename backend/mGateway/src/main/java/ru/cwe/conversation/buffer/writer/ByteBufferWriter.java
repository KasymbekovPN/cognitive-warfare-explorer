package ru.cwe.conversation.buffer.writer;

import io.netty.buffer.ByteBuf;

public interface ByteBufferWriter<E> {
	void write(ByteBuf buffer, E element);
}
