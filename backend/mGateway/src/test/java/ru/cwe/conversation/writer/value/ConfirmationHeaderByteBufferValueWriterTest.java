package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.Priorities;
import ru.cwe.conversation.message.Versions;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;
import utils.BufferUtil;
import utils.faker.Fakers;

import static org.assertj.core.api.Assertions.assertThat;

class ConfirmationHeaderByteBufferValueWriterTest {

	@Test
	void shouldCheckWriting() {
		int expectedVersion = Fakers.message().version();
		int expectedPriority = Fakers.message().priority();
		MessageType expectedType = Fakers.message().messageType();
		ConfirmationResult expectedResult = Fakers.message().confirmationResult();

		Integer[] header = new Integer[4];
		header[0] = expectedVersion;
		header[1] = expectedPriority;
		header[2] = expectedType.getValue();
		header[3] = expectedResult.getValue();
		ByteBuf buffer = BufferUtil.create();
		new ConfirmationHeaderByteBufferValueWriter().write(buffer, header);

		char h0 = buffer.readChar();
		char h1 = buffer.readChar();
		int version = h0 & Versions.MAX;
		int priority = (h0 >> 10) & Priorities.MAX;
		MessageType type = MessageType.valueOf(0b11 & h1);
		ConfirmationResult result = ConfirmationResult.valueOf(0b11 & (h1 >> 2));

		assertThat(version).isEqualTo(expectedVersion);
		assertThat(priority).isEqualTo(expectedPriority);
		assertThat(type).isEqualTo(expectedType);
		assertThat(result).isEqualTo(expectedResult);
	}
}
