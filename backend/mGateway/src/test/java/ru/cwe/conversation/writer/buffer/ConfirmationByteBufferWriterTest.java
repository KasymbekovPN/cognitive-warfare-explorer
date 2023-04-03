package ru.cwe.conversation.writer.buffer;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;
import ru.cwe.conversation.writer.value.ByteBufferValueWriter;
import utils.TestConfirmationMessage;
import utils.faker.Fakers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ConfirmationByteBufferWriterTest {

	@Test
	void shouldCheckWriting() {
		int expectedVersion = Fakers.message().version();
		int expectedPriority = Fakers.message().priority();
		UUID expectedUuid = Fakers.base().uuid().uuid();
		ConfirmationResult expectedResult = Fakers.message().confirmationResult();
		String expectedPayloadMessageType = Fakers.base().string().string();
		TestConfirmationMessage message = new TestConfirmationMessage(
			expectedVersion,
			expectedPriority,
			expectedUuid,
			expectedResult,
			expectedPayloadMessageType
		);

		TestHeaderWriter headersWriter = new TestHeaderWriter();
		TestUuidWriter uuidWriter = new TestUuidWriter();
		TestStringWriter stringWriter = new TestStringWriter();
		new ConfirmationByteBufferWriter(
			headersWriter,
			uuidWriter,
			stringWriter
		).write(null, message);

		assertThat(headersWriter.getVersion()).isEqualTo(expectedVersion);
		assertThat(headersWriter.getPriority()).isEqualTo(expectedPriority);
		assertThat(headersWriter.getType()).isEqualTo(MessageType.CONFIRMATION);
		assertThat(headersWriter.getResult()).isEqualTo(expectedResult);
		assertThat(uuidWriter.getUuid()).isEqualTo(expectedUuid);
		assertThat(stringWriter.getString()).isEqualTo(expectedPayloadMessageType);
	}

	@Getter
	private static class TestHeaderWriter implements ByteBufferValueWriter<Integer[]>{
		private int version;
		private int priority;
		private MessageType type;
		private ConfirmationResult result;

		@Override
		public void write(ByteBuf buffer, Integer[] element) {
			version = element[0];
			priority = element[1];
			type = MessageType.valueOf(element[2]);
			result = ConfirmationResult.valueOf(element[3]);
		}
	}

	@Getter
	private static class TestUuidWriter implements ByteBufferValueWriter<UUID>{
		private UUID uuid;
		@Override
		public void write(ByteBuf buffer, UUID element) {
			this.uuid = element;
		}
	}

	@Getter
	private static class TestStringWriter implements ByteBufferValueWriter<String>{
		private String string;
		@Override
		public void write(ByteBuf buffer, String element) {
			this.string = element;
		}
	}
}
