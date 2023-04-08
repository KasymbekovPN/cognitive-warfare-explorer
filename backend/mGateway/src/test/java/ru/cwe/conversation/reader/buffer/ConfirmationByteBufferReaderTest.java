package ru.cwe.conversation.reader.buffer;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.reader.value.ByteBufferValueReader;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;
import utils.faker.Fakers;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ConfirmationByteBufferReaderTest {
	private int expectedVersion;
	private int expectedPriority;
	private UUID expectedUuid;
	private ConfirmationResult expectedResult;
	private String expectedPayloadMessageType;

	@BeforeEach
	void setUp() {
		expectedVersion = Fakers.message().version();
		expectedPriority = Fakers.message().priority();
		expectedUuid = Fakers.base().uuid().uuid();
		expectedResult = ConfirmationResult.REQUEST;
		expectedPayloadMessageType = Fakers.base().string().string();
	}

	@Test
	void shouldCheckReading_readingException() {
		ConfirmationByteBufferReader reader = ConfirmationByteBufferReader.builder()
			.header(new TestHeaderReader(true))
			.uuid(createUuidReader())
			.string(createStringReader())
			.build();

		Optional<ConfirmationMessage> maybeResult = reader.read(null);
		assertThat(maybeResult).isEmpty();
	}

	@Test
	void shouldCheckReading_ifMessageTypeInvalid() {
		TestHeaderReader headerReader = new TestHeaderReader(
			expectedVersion,
			expectedPriority,
			MessageType.REQUEST,
			ConfirmationResult.RESPONSE
		);
		ConfirmationByteBufferReader reader = ConfirmationByteBufferReader.builder()
			.header(headerReader)
			.uuid(createUuidReader())
			.string(createStringReader())
			.build();
		Optional<ConfirmationMessage> maybeResult = reader.read(null);

		assertThat(maybeResult).isEmpty();
	}

	@Test
	void shouldCheckReading() {
		TestHeaderReader headerReader = new TestHeaderReader(
			expectedVersion,
			expectedPriority,
			MessageType.CONFIRMATION,
			expectedResult
		);
		ConfirmationByteBufferReader reader = ConfirmationByteBufferReader.builder()
			.header(headerReader)
			.uuid(createUuidReader())
			.string(createStringReader())
			.build();
		Optional<ConfirmationMessage> maybeResult = reader.read(null);

		assertThat(maybeResult).isPresent();
		ConfirmationMessage message = maybeResult.get();
		assertThat(message.getVersion()).isEqualTo(expectedVersion);
		assertThat(message.getPriority()).isEqualTo(expectedPriority);
		assertThat(message.getUuid()).isEqualTo(expectedUuid);
		assertThat(message.getType()).isEqualTo(MessageType.CONFIRMATION);
		assertThat(message.getResult()).isEqualTo(expectedResult);
		assertThat(message.getPayloadMessageType()).isEqualTo(expectedPayloadMessageType);
	}

	private TestUuidReader createUuidReader(){
		TestUuidReader reader = Mockito.mock(TestUuidReader.class);
		Mockito
			.when(reader.read(Mockito.anyObject()))
			.thenReturn(expectedUuid);
		return reader;
	}

	private TestStringReader createStringReader(){
		TestStringReader reader = Mockito.mock(TestStringReader.class);
		Mockito
			.when(reader.read(Mockito.anyObject()))
			.thenReturn(expectedPayloadMessageType);
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
