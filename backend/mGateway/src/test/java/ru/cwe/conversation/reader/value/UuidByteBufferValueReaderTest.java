package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import utils.BufferUtil;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

// TODO: 01.04.2023 !!!
class UuidByteBufferValueReaderTest {

	@Test
	void shouldCheckReading_ifFail() {
		Throwable throwable = catchThrowable(() -> {
			new UuidByteBufferValueReader().read(
				BufferUtil.create()
			);
		});

		assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
	}

	@Test
	void shouldCheckReading() {
		UUID expectedUuid = UUID.randomUUID();
		ByteBuf buffer = BufferUtil.create();
		BufferUtil.writeUuid(buffer, expectedUuid);

		UUID uuid = new UuidByteBufferValueReader().read(buffer);
		assertThat(uuid).isEqualTo(expectedUuid);
	}
}
