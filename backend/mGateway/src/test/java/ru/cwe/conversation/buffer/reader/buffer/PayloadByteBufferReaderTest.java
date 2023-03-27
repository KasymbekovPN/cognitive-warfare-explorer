package ru.cwe.conversation.buffer.reader.buffer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.buffer.reader.value.ByteBufferValueReader;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.payload.PayloadMessage;
import utils.TestAddress;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PayloadByteBufferReaderTest {
	private static final UUID MESSAGE_UUID = UUID.randomUUID();
	private static final String STRING = "some.content";
	private static final Address ADDRESS = new TestAddress("from", 8080);

	@Test
	void shouldCheckReading_ifTypeInvalid() {
		PayloadByteBufferReader reader = new PayloadByteBufferReader(
			createMessageReader(MessageType.INVALID),
			createUuidReader(),
			createStringReader(),
			createAddressReader()
		);

		Optional<PayloadMessage> result = reader.read(null);
		assertThat(result).isEmpty();
	}

	@Test
	void shouldCheckReading_ifTypeConversation() {
		PayloadByteBufferReader reader = new PayloadByteBufferReader(
			createMessageReader(MessageType.CONFIRMATION),
			createUuidReader(),
			createStringReader(),
			createAddressReader()
		);

		Optional<PayloadMessage> result = reader.read(null);
		assertThat(result).isEmpty();
	}

	@Test
	void shouldCheckReading_ifTypeRequest() {
		PayloadByteBufferReader reader = new PayloadByteBufferReader(
			createMessageReader(MessageType.REQUEST),
			createUuidReader(),
			createStringReader(),
			createAddressReader()
		);

		Optional<PayloadMessage> result = reader.read(null);
		assertThat(result).isPresent();

		PayloadMessage message = result.get();
		assertThat(message.getUuid()).isEqualTo(MESSAGE_UUID);
		assertThat(message.getType()).isEqualTo(MessageType.REQUEST);
		assertThat(message.getContentType()).isEqualTo(STRING);
		assertThat(message.getContent()).isEqualTo(STRING);
		assertThat(message.getFrom()).isEqualTo(ADDRESS);
		assertThat(message.getTo()).isEqualTo(ADDRESS);
	}

	@Test
	void shouldCheckReading_ifTypeResponse() {
		PayloadByteBufferReader reader = new PayloadByteBufferReader(
			createMessageReader(MessageType.RESPONSE),
			createUuidReader(),
			createStringReader(),
			createAddressReader()
		);

		Optional<PayloadMessage> result = reader.read(null);
		assertThat(result).isPresent();

		PayloadMessage message = result.get();
		assertThat(message.getUuid()).isEqualTo(MESSAGE_UUID);
		assertThat(message.getType()).isEqualTo(MessageType.RESPONSE);
		assertThat(message.getContentType()).isEqualTo(STRING);
		assertThat(message.getContent()).isEqualTo(STRING);
		assertThat(message.getFrom()).isEqualTo(ADDRESS);
		assertThat(message.getTo()).isEqualTo(ADDRESS);
	}

	private TestMessageTypeReader createMessageReader(MessageType type){
		TestMessageTypeReader reader = Mockito.mock(TestMessageTypeReader.class);
		Mockito
			.when(reader.read(Mockito.anyObject()))
			.thenReturn(type);
		return reader;
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
			.thenReturn(STRING);
		return reader;
	}

	private TestAddressReader createAddressReader(){
		TestAddressReader reader = Mockito.mock(TestAddressReader.class);
		Mockito
			.when(reader.read(Mockito.anyObject()))
			.thenReturn(ADDRESS);
		return reader;
	}

	private static abstract class TestMessageTypeReader implements ByteBufferValueReader<MessageType> {}
	private static abstract class TestUuidReader implements ByteBufferValueReader<UUID>{}
	private static abstract class TestStringReader implements ByteBufferValueReader<String>{}
	private static abstract class TestAddressReader implements ByteBufferValueReader<Address>{}
}
