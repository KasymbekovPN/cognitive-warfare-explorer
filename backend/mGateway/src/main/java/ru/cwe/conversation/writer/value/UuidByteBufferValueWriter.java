package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

public final class UuidByteBufferValueWriter implements ByteBufferValueWriter<UUID> {

	@Override
	public void write(final ByteBuf buffer, final UUID element) {
		buffer.writeLong(element.getLeastSignificantBits());
		buffer.writeLong(element.getMostSignificantBits());
	}
}
