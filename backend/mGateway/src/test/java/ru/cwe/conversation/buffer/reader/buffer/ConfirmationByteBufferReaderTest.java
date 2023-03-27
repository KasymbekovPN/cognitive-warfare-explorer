package ru.cwe.conversation.buffer.reader.buffer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.cwe.conversation.buffer.reader.value.ByteBufferValueReader;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ConfirmationByteBufferReaderTest {
	private static final MessageType INVALID_MESSAGE_TYPE = MessageType.INVALID;
	private static final MessageType MESSAGE_TYPE = MessageType.CONFIRMATION;
	private static final UUID MESSAGE_UUID = UUID.randomUUID();
	private static final ConfirmationResult CONFIRMATION_RESULT = ConfirmationResult.REQUEST;
	private static final String PAYLOAD_MESSAGE_TYPE = "some.payload.message.type";

	@Test
	void shouldCheckReading_ifMessageTypeInvalid() {
		ConfirmationByteBufferReader reader = new ConfirmationByteBufferReader(
			createInvalidMessageTypeReader(),
			createUuidReader(),
			createConfirmationResultReader(),
			createPayloadMessageTypeReader()
		);
		Optional<ConfirmationMessage> maybeResult = reader.read(null);

		assertThat(maybeResult).isEmpty();
	}

	@Test
	void shouldCheckReading() {
		ConfirmationByteBufferReader reader = new ConfirmationByteBufferReader(
			createMessageTypeReader(),
			createUuidReader(),
			createConfirmationResultReader(),
			createPayloadMessageTypeReader()
		);
		Optional<ConfirmationMessage> maybeResult = reader.read(null);

		assertThat(maybeResult).isPresent();
		ConfirmationMessage message = maybeResult.get();
		assertThat(message.getUuid()).isEqualTo(MESSAGE_UUID);
		assertThat(message.getType()).isEqualTo(MessageType.CONFIRMATION);
		assertThat(message.getResult()).isEqualTo(CONFIRMATION_RESULT);
		assertThat(message.getPayloadMessageType()).isEqualTo(PAYLOAD_MESSAGE_TYPE);
	}

	private TestMessageTypeReader createInvalidMessageTypeReader(){
		TestMessageTypeReader reader = Mockito.mock(TestMessageTypeReader.class);
		Mockito
			.when(reader.read(Mockito.anyObject()))
			.thenReturn(INVALID_MESSAGE_TYPE);
		return reader;
	}

	private TestMessageTypeReader createMessageTypeReader(){
		TestMessageTypeReader reader = Mockito.mock(TestMessageTypeReader.class);
		Mockito
			.when(reader.read(Mockito.anyObject()))
			.thenReturn(MESSAGE_TYPE);
		return reader;
	}

	private TestUuidReader createUuidReader(){
		TestUuidReader reader = Mockito.mock(TestUuidReader.class);
		Mockito
			.when(reader.read(Mockito.anyObject()))
			.thenReturn(MESSAGE_UUID);
		return reader;
	}

	private TestConfirmationResultReader createConfirmationResultReader(){
		TestConfirmationResultReader reader = Mockito.mock(TestConfirmationResultReader.class);
		Mockito
			.when(reader.read(Mockito.anyObject()))
			.thenReturn(CONFIRMATION_RESULT);
		return reader;
	}

	private TestPayloadMessageTypeReader createPayloadMessageTypeReader(){
		TestPayloadMessageTypeReader reader = Mockito.mock(TestPayloadMessageTypeReader.class);
		Mockito
			.when(reader.read(Mockito.anyObject()))
			.thenReturn(PAYLOAD_MESSAGE_TYPE);
		return reader;
	}

	private static abstract class TestMessageTypeReader implements ByteBufferValueReader<MessageType>{}
	private static abstract class TestUuidReader implements ByteBufferValueReader<UUID>{}
	private static abstract class TestConfirmationResultReader implements ByteBufferValueReader<ConfirmationResult>{}
	private static abstract class TestPayloadMessageTypeReader implements ByteBufferValueReader<String>{}
}
