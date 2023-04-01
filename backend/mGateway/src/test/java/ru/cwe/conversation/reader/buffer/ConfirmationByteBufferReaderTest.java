package ru.cwe.conversation.reader.buffer;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.reader.value.ByteBufferValueReader;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ConfirmationByteBufferReaderTest {
	private static final int EXPECTED_VERSION = 1;
	private static final int EXPECTED_PRIORITY = 2;
	private static final UUID MESSAGE_UUID = UUID.randomUUID();
	private static final ConfirmationResult CONFIRMATION_RESULT = ConfirmationResult.REQUEST;
	private static final String PAYLOAD_MESSAGE_TYPE = "some.payload.message.type";

	@Test
	void shouldCheckReading_readingException() {
		ConfirmationByteBufferReader reader = new ConfirmationByteBufferReader(
			new TestHeaderReader(true),
			createUuidReader(),
			createStringReader()
		);

		Optional<ConfirmationMessage> maybeResult = reader.read(null);
		assertThat(maybeResult).isEmpty();
	}

	@Test
	void shouldCheckReading_ifMessageTypeInvalid() {
		TestHeaderReader headerReader = new TestHeaderReader(
			EXPECTED_VERSION,
			EXPECTED_PRIORITY,
			MessageType.REQUEST,
			ConfirmationResult.RESPONSE
		);
		ConfirmationByteBufferReader reader = new ConfirmationByteBufferReader(
			headerReader,
			createUuidReader(),
			createStringReader()
		);
		Optional<ConfirmationMessage> maybeResult = reader.read(null);

		assertThat(maybeResult).isEmpty();
	}

	@Test
	void shouldCheckReading() {
		TestHeaderReader headerReader = new TestHeaderReader(
			EXPECTED_VERSION,
			EXPECTED_PRIORITY,
			MessageType.CONFIRMATION,
			ConfirmationResult.REQUEST
		);
		ConfirmationByteBufferReader reader = new ConfirmationByteBufferReader(
			headerReader,
			createUuidReader(),
			createStringReader()
		);
		Optional<ConfirmationMessage> maybeResult = reader.read(null);

		assertThat(maybeResult).isPresent();
		ConfirmationMessage message = maybeResult.get();
		assertThat(message.getVersion()).isEqualTo(EXPECTED_VERSION);
		assertThat(message.getPriority()).isEqualTo(EXPECTED_PRIORITY);
		assertThat(message.getUuid()).isEqualTo(MESSAGE_UUID);
		assertThat(message.getType()).isEqualTo(MessageType.CONFIRMATION);
		assertThat(message.getResult()).isEqualTo(CONFIRMATION_RESULT);
		assertThat(message.getPayloadMessageType()).isEqualTo(PAYLOAD_MESSAGE_TYPE);
	}

	private TestUuidReader createUuidReader(){
		TestUuidReader reader = Mockito.mock(TestUuidReader.class);
		Mockito
			.when(reader.read(Mockito.anyObject()))
			.thenReturn(MESSAGE_UUID);
		return reader;
	}

	private TestStringReader createStringReader(){
		TestStringReader reader = Mockito.mock(TestStringReader.class);
		Mockito
			.when(reader.read(Mockito.anyObject()))
			.thenReturn(PAYLOAD_MESSAGE_TYPE);
		return reader;
	}

	private static class TestHeaderReader implements ByteBufferValueReader<Integer[]>{
		private boolean doThrow;
		private int version;
		private int priority;
		private MessageType type;
		private ConfirmationResult result;

		public TestHeaderReader(boolean doThrow) {
			this.doThrow = doThrow;
		}

		public TestHeaderReader(int version, int priority, MessageType type, ConfirmationResult result) {
			this.version = version;
			this.priority = priority;
			this.type = type;
			this.result = result;
		}

		@Override
		public Integer[] read(ByteBuf buffer) {
			if (doThrow){
				throw new RuntimeException("");
			}
			Integer[] r = new Integer[4];
			r[0] = version;
			r[1] = priority;
			r[2] = type.getValue();
			r[3] = result.getValue();
			return r;
		}
	}

	private static abstract class TestUuidReader implements ByteBufferValueReader<UUID>{}
	private static abstract class TestStringReader implements ByteBufferValueReader<String>{}
}
