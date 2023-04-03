package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.Priorities;
import ru.cwe.conversation.message.Versions;
import utils.BufferUtil;
import utils.faker.Fakers;

import static org.assertj.core.api.Assertions.assertThat;

class PayloadHeaderByteBufferValueWriterTest {

	@Test
	void shouldCheckWriting() {
		int expectedVersion = Fakers.message().version();
		int expectedPriority = Fakers.message().priority();
		MessageType expectedType = Fakers.message().messageType();

		Integer[] header = new Integer[3];
		header[0] = expectedVersion;
		header[1] = expectedPriority;
		header[2] = expectedType.getValue();
		ByteBuf buffer = BufferUtil.create();
		new PayloadHeaderByteBufferValueWriter().write(buffer, header);

		char h0 = buffer.readChar();
		char h1 = buffer.readChar();
		int version = h0 & Versions.MAX;
		int priority = (h0 >> 10) & Priorities.MAX;
		MessageType type = MessageType.valueOf(0b11 & h1);

		assertThat(version).isEqualTo(expectedVersion);
		assertThat(priority).isEqualTo(expectedPriority);
		assertThat(type).isEqualTo(expectedType);
	}
}
