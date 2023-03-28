package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;

// TODO: 26.03.2023 ???
//private final String payloadMessageType;
//private final Address to;

public interface ByteBufferValueReader<R> {
	R read(ByteBuf buffer);
}
