package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;
import utils.BufferUtil;

import static org.assertj.core.api.Assertions.assertThat;

class ConfirmationResultByteBufferValueWriterTest {

	@Test
	void shouldCheckWriting() {
		ConfirmationResult expectedResult = ConfirmationResult.REQUEST;
		ByteBuf buffer = BufferUtil.create();
		new ConfirmationResultByteBufferValueWriter().write(buffer, expectedResult);

		assertThat(BufferUtil.readConfirmationResult(buffer)).isEqualTo(expectedResult);
	}
}
