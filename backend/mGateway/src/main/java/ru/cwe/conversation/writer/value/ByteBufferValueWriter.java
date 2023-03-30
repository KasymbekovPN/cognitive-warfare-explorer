package ru.cwe.conversation.writer.value;

// TODO: 27.03.2023 ????
//private final MessageType type = MessageType.CONFIRMATION;
//private final ConfirmationResult result;
//private final String payloadMessageType;
//private final Address to;

import io.netty.buffer.ByteBuf;

public interface ByteBufferValueWriter<E> {
	void write(ByteBuf buffer, E element);
}
