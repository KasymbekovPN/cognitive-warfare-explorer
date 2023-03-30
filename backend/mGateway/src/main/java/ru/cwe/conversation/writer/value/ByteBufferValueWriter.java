package ru.cwe.conversation.writer.value;

// TODO: 27.03.2023 ????
//private final Address to;

import io.netty.buffer.ByteBuf;

public interface ByteBufferValueWriter<E> {
	void write(ByteBuf buffer, E element);
}
