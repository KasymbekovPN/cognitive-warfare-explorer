package ru.cwe.conversation.buffer.reader;

import io.netty.buffer.ByteBuf;

public interface ByteBufferReader<R> {
	R read(ByteBuf buffer);
}
