package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import utils.BufferUtil;
import utils.faker.Fakers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ConfirmationHeaderByteBufferValueReaderTest {

	@Test
	void shouldCheckReading_ifIndexOutOfBoundsException() {
		Throwable throwable = catchThrowable(() -> {
			new ConfirmationHeaderByteBufferValueReader().read(
				BufferUtil.create()
			);
		});

		assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckReading_ifHeaderReadingException.csv")
	void shouldCheckReading_ifHeaderReadingException(int type, int result, String expected) {
		int version = Fakers.message().version();
		int priority = Fakers.message().priority();

		ByteBuf buffer = BufferUtil.create();
		buffer.writeChar(version + (priority << 10));
		buffer.writeChar(type + (result << 3));

		Throwable throwable = catchThrowable(() -> {
			new ConfirmationHeaderByteBufferValueReader().read(buffer);
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
		int result = Fakers.message().confirmationResult().getValue();

		ByteBuf buffer = BufferUtil.create();
		buffer.writeChar(version + (priority << 10));
		buffer.writeChar(type + (result << 3));

		Integer[] headers = new ConfirmationHeaderByteBufferValueReader().read(buffer);
		assertThat(headers[0]).isEqualTo(version);
		assertThat(headers[1]).isEqualTo(priority);
		assertThat(headers[2]).isEqualTo(type);
		assertThat(headers[3]).isEqualTo(result);
	}
}
