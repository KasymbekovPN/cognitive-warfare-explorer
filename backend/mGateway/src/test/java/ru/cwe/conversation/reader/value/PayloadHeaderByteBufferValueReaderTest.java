package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckReading_ifPayloadHeaderReadingException.csv")
	void shouldCheckReading_ifPayloadHeaderReadingException(int type, String expected) {
		int version = Fakers.message().version();
		int priority = Fakers.message().priority();

		ByteBuf buffer = BufferUtil.create();
		buffer.writeChar(version + (priority << 10));
		buffer.writeChar(type);

		Throwable throwable = catchThrowable(() -> {
			new PayloadHeaderByteBufferValueReader().read(buffer);
		});
		assertThat(throwable)
			.isInstanceOf(HeaderReadingException.class)
			.hasMessage(expected);
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
