package ru.cwe.conversation.buffer.reader.value;

import io.netty.buffer.ByteBuf;

// TODO: 26.03.2023 ???
//private final UUID uuid;
//private final MessageType type = MessageType.CONFIRMATION;
//private final ConfirmationResult result;
//private final String payloadMessageType;
//private final Address to;

public interface ByteBufferValueReader<R> {
	R read(ByteBuf buffer);
}
