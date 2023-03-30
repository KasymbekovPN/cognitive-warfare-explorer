package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;

import java.nio.charset.Charset;

@RequiredArgsConstructor
public final class StringByteBufferValueWriter implements ByteBufferValueWriter<String> {
	private final Charset charset;

	@Override
	public void write(ByteBuf buffer, String element) {
		int length = element == null ? -1 : element.length();
		buffer.writeInt(length);
		if (length > 0){
			buffer.writeCharSequence(element, charset);
		}
	}
}
