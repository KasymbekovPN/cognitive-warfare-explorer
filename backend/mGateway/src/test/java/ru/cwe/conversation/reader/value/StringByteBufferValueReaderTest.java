package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import utils.TestBuffers;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class StringByteBufferValueReaderTest {
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	@Test
	void shouldCheckReading_ifFail() {
		Throwable throwable = catchThrowable(() -> {
			new StringByteBufferValueReader(CHARSET).read(
				TestBuffers.create()
			);
		});
		assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
	}

	@Test
	void shouldCheckReading_ifStringIsNull() {
		ByteBuf buffer = TestBuffers.create();
		TestBuffers.writeString(buffer, null);

		String nullLine = new StringByteBufferValueReader(CHARSET).read(buffer);
		assertThat(nullLine).isNull();
	}

	@Test
	void shouldCheckReading_ifStringIsEmpty() {
		ByteBuf buffer = TestBuffers.create();
		TestBuffers.writeString(buffer, "");

		String emptyLine = new StringByteBufferValueReader(CHARSET).read(buffer);
		assertThat(emptyLine).isEmpty();
	}

	@Test
	void shouldCheckReading() {
		String expectedLine = "some.line";
		ByteBuf buffer = TestBuffers.create();
		TestBuffers.writeString(buffer, expectedLine);

		String line = new StringByteBufferValueReader(CHARSET).read(buffer);
		assertThat(line).isEqualTo(expectedLine);
	}
}
