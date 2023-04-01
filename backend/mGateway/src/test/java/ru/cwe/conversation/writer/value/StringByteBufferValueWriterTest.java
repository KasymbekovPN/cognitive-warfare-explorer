package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import utils.BufferUtil;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: 01.04.2023 !!!
class StringByteBufferValueWriterTest {

	@Test
	void shouldCheckWriting_ifNull() {
		ByteBuf buffer = BufferUtil.create();
		new StringByteBufferValueWriter(StandardCharsets.UTF_8).write(buffer, null);

		assertThat(BufferUtil.readString(buffer)).isNull();
	}

	@Test
	void shouldCheckWriting_ifEmpty() {
		ByteBuf buffer = BufferUtil.create();
		new StringByteBufferValueWriter(StandardCharsets.UTF_8).write(buffer, "");

		assertThat(BufferUtil.readString(buffer)).isEmpty();
	}

	@Test
	void shouldCheckWriting() {
		String expected = "some.string";
		ByteBuf buffer = BufferUtil.create();
		new StringByteBufferValueWriter(StandardCharsets.UTF_8).write(buffer, expected);

		assertThat(BufferUtil.readString(buffer)).isEqualTo(expected);
	}
}
