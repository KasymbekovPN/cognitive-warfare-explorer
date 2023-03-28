package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;

import java.nio.charset.Charset;

@RequiredArgsConstructor
public final class StringByteBufferValueReader implements ByteBufferValueReader<String> {
	private static final int LOWER_LEN_VALUE_THRESHOLD = -1;
	private static final int EMPTY_STRING_LEN = 0;

	private final Charset charset;

	@Override
	public String read(ByteBuf buffer) {
		int len = buffer.readInt();
		if (len <= LOWER_LEN_VALUE_THRESHOLD){
			return null;
		}
		if (len == EMPTY_STRING_LEN){
			return "";
		}

		return buffer.readCharSequence(len, charset).toString();
	}
}
