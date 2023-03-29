package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;

public interface ByteBufferValueReader<R> {
	R read(ByteBuf buffer);
}
