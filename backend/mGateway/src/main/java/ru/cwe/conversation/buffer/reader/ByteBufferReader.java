package ru.cwe.conversation.buffer.reader;

import io.netty.buffer.ByteBuf;

import java.util.Optional;

public interface ByteBufferReader<R> {
	Optional<R> read(ByteBuf buffer);
}
