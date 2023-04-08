package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import utils.BufferUtil;
import utils.faker.Fakers;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class StringByteBufferValueWriterTest {

	@Test
	void shouldCheckWriting_ifNull() {
		ByteBuf buffer = BufferUtil.create();
		StringByteBufferValueWriter.instance().write(buffer, null);

		assertThat(BufferUtil.readString(buffer)).isNull();
	}

	@Test
	void shouldCheckWriting_ifEmpty() {
		ByteBuf buffer = BufferUtil.create();
		StringByteBufferValueWriter.instance().write(buffer, "");

		assertThat(BufferUtil.readString(buffer)).isEmpty();
	}

	@Test
	void shouldCheckWriting() {
		String expected = Fakers.base().string().string();
		ByteBuf buffer = BufferUtil.create();
		StringByteBufferValueWriter.instance().write(buffer, expected);

		assertThat(BufferUtil.readString(buffer)).isEqualTo(expected);
	}
}
