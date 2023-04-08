package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import utils.BufferUtil;
import utils.faker.Fakers;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class StringByteBufferValueReaderTest {
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	@Test
	void shouldCheckReading_ifFail() {
		Throwable throwable = catchThrowable(() -> {
			StringByteBufferValueReader.builder().build().read(
				BufferUtil.create()
			);
		});
		assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
	}

	@Test
	void shouldCheckReading_ifStringIsNull() {
		ByteBuf buffer = BufferUtil.create();
		BufferUtil.writeString(buffer, null);

		String nullLine = StringByteBufferValueReader.builder().build().read(buffer);
		assertThat(nullLine).isNull();
	}

	@Test
	void shouldCheckReading_ifStringIsEmpty() {
		ByteBuf buffer = BufferUtil.create();
		BufferUtil.writeString(buffer, "");

		String emptyLine = StringByteBufferValueReader.builder().build().read(buffer);
		assertThat(emptyLine).isEmpty();
	}

	@Test
	void shouldCheckReading() {
		String expectedLine = Fakers.base().string().string();
		ByteBuf buffer = BufferUtil.create();
		BufferUtil.writeString(buffer, expectedLine);

		String line = StringByteBufferValueReader.builder().build().read(buffer);
		assertThat(line).isEqualTo(expectedLine);
	}
}
