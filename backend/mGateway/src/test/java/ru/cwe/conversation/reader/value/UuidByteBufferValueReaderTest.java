package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import utils.TestBuffers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class UuidByteBufferValueReaderTest {

	@Test
	void shouldCheckReading_ifFail() {
		Throwable throwable = catchThrowable(() -> {
			new UuidByteBufferValueReader().read(
				TestBuffers.create()
			);
		});

		assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
	}

	@Test
	void shouldCheckReading() {
		UUID expectedUuid = UUID.randomUUID();
		ByteBuf buffer = TestBuffers.create();
		TestBuffers.writeUuid(buffer, expectedUuid);

		UUID uuid = new UuidByteBufferValueReader().read(buffer);
		assertThat(uuid).isEqualTo(expectedUuid);
	}
}
