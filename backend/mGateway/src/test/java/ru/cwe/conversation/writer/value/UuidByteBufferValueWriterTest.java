package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import utils.BufferUtil;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UuidByteBufferValueWriterTest {

	@Test
	void shouldCheckWriting() {
		UUID expectedUuid = UUID.randomUUID();
		ByteBuf buffer = BufferUtil.create();
		new UuidByteBufferValueWriter().write(buffer, expectedUuid);

		assertThat(BufferUtil.readUuid(buffer)).isEqualTo(expectedUuid);
	}
}
