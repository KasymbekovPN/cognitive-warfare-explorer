package ru.cwe.conversation.reader.buffer;

import io.netty.buffer.ByteBuf;

import java.util.Optional;

public abstract class BaseByteBufferReader<R> implements ByteBufferReader<R> {

	@Override
	public Optional<R> read(ByteBuf buffer) {
		try {
			return readUnsafe(buffer);
		} catch (IndexOutOfBoundsException ex){
			ex.printStackTrace();
			return Optional.empty();
		}
	}

	protected abstract Optional<R> readUnsafe(ByteBuf buffer);
}
