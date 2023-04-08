package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class StringByteBufferValueReader implements ByteBufferValueReader<String> {
	private static final int LOWER_LEN_VALUE_THRESHOLD = -1;
	private static final int EMPTY_STRING_LEN = 0;

	private final Charset charset;

	public static Builder builder(){
		return new Builder();
	}

	public static StringByteBufferValueReader instance(){
		return builder().build();
	}

	private StringByteBufferValueReader(Charset charset) {
		this.charset = charset;
	}

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

	public static class Builder {
		private Charset charset;

		public Builder charset(Charset charset){
			this.charset = charset;
			return this;
		}

		public StringByteBufferValueReader build(){
			return new StringByteBufferValueReader(
				charset != null ? charset : StandardCharsets.UTF_8
			);
		}
	}
}
