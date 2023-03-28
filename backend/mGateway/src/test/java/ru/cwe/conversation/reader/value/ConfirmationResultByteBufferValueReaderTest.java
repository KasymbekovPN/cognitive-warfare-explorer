package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;
import utils.TestBuffers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ConfirmationResultByteBufferValueReaderTest {

	@Test
	void shouldCheckReading_ifFail() {
		Throwable throwable = catchThrowable(() -> {
			new ConfirmationResultByteBufferValueReader().read(
				TestBuffers.create()
			);
		});
		assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckReading_confirmationResult.csv")
	void shouldCheckReading(int intSource, String strSource) {
		ByteBuf buffer = TestBuffers.create();
		TestBuffers.writeInt(buffer, intSource);

		ConfirmationResult result = new ConfirmationResultByteBufferValueReader().read(buffer);
		assertThat(result).isEqualTo(ConfirmationResult.valueOf(strSource));
	}
}
