package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

public final class UuidByteBufferValueReader implements ByteBufferValueReader<UUID> {

	@Override
	public UUID read(final ByteBuf buffer) {
		long low = buffer.readLong();
		return new UUID(buffer.readLong(), low);
	}
}
