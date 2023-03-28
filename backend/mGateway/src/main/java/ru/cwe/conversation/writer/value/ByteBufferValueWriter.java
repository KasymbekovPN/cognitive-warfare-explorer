package ru.cwe.conversation.writer.value;

// TODO: 27.03.2023 ????
//private final UUID uuid;
//private final MessageType type = MessageType.CONFIRMATION;
//private final ConfirmationResult result;
//private final String payloadMessageType;
//private final Address to;

import java.nio.ByteBuffer;

public interface ByteBufferValueWriter<E> {
	void write(ByteBuffer buffer, E element);
}
