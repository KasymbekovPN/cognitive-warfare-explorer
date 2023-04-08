package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class StringByteBufferValueWriter implements ByteBufferValueWriter<String> {
	private final Charset charset;

	public static Builder builder(){
		return new Builder();
	}

	public static StringByteBufferValueWriter instance(){
		return builder().build();
	}

	private StringByteBufferValueWriter(Charset charset) {
		this.charset = charset;
	}

	@Override
	public void write(ByteBuf buffer, String element) {
		int length = element == null ? -1 : element.length();
		buffer.writeInt(length);
		if (length > 0){
			buffer.writeCharSequence(element, charset);
		}
	}

	public static class Builder {
		private Charset charset;

		public Builder charset(Charset charset){
			this.charset = charset;
			return this;
		}

		public StringByteBufferValueWriter build(){
			return new StringByteBufferValueWriter(
				charset != null ? charset : StandardCharsets.UTF_8
			);
		}
	}
}
