package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import utils.BufferUtil;
import utils.faker.Fakers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class PayloadHeaderByteBufferValueReaderTest {


	@Test
	void shouldCheckReading_ifIndexOutOfBoundsException() {
		Throwable throwable = catchThrowable(() -> {
			new PayloadHeaderByteBufferValueReader().read(
				BufferUtil.create()
			);
		});

		assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
	}

	@Test
	void shouldCheckReading() {
		int version = Fakers.message().version();
		int priority = Fakers.message().priority();
		int type = Fakers.message().messageType().getValue();

		ByteBuf buffer = BufferUtil.create();
		buffer.writeChar(version + (priority << 10));
		buffer.writeChar(type);

		Integer[] headers = new PayloadHeaderByteBufferValueReader().read(buffer);
		assertThat(headers[0]).isEqualTo(version);
		assertThat(headers[1]).isEqualTo(priority);
		assertThat(headers[2]).isEqualTo(type);
	}
}
