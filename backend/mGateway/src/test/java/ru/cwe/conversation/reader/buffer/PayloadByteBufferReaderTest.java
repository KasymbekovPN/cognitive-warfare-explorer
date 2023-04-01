package ru.cwe.conversation.reader.buffer;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.reader.value.ByteBufferValueReader;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.payload.PayloadMessage;
import utils.TestAddress;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: 01.04.2023 !!!
class PayloadByteBufferReaderTest {
	private static final int EXPECTED_VERSION = 1;
	private static final int EXPECTED_PRIORITY = 2;
	private static final UUID EXPECTED_UUID = UUID.randomUUID();
	private static final String EXPECTED_STRING = "some.content";
	private static final Address EXPECTED_ADDRESS = new TestAddress("from", 8080);

	@Test
	void shouldCheckReading_ifException() {
		PayloadByteBufferReader reader = new PayloadByteBufferReader(
			new TestHeaderReader(true),
			createUuidReader(),
			createStringReader(),
			createAddressReader()
		);

		Optional<PayloadMessage> result = reader.read(null);
		assertThat(result).isEmpty();
	}

	@Test
	void shouldCheckReading_ifTypeInvalid() {
		PayloadByteBufferReader reader = new PayloadByteBufferReader(
			new TestHeaderReader(EXPECTED_VERSION, EXPECTED_PRIORITY, MessageType.INVALID),
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
			new TestHeaderReader(EXPECTED_VERSION, EXPECTED_PRIORITY, MessageType.CONFIRMATION),
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
			new TestHeaderReader(EXPECTED_VERSION, EXPECTED_PRIORITY, MessageType.REQUEST),
			createUuidReader(),
			createStringReader(),
			createAddressReader()
		);

		Optional<PayloadMessage> result = reader.read(null);
		assertThat(result).isPresent();

		PayloadMessage message = result.get();
		assertThat(message.getVersion()).isEqualTo(EXPECTED_VERSION);
		assertThat(message.getPriority()).isEqualTo(EXPECTED_PRIORITY);
		assertThat(message.getUuid()).isEqualTo(EXPECTED_UUID);
		assertThat(message.getType()).isEqualTo(MessageType.REQUEST);
		assertThat(message.getContentType()).isEqualTo(EXPECTED_STRING);
		assertThat(message.getContent()).isEqualTo(EXPECTED_STRING);
		assertThat(message.getFrom()).isEqualTo(EXPECTED_ADDRESS);
		assertThat(message.getTo()).isEqualTo(EXPECTED_ADDRESS);
	}

	@Test
	void shouldCheckReading_ifTypeResponse() {
		PayloadByteBufferReader reader = new PayloadByteBufferReader(
			new TestHeaderReader(EXPECTED_VERSION, EXPECTED_PRIORITY, MessageType.RESPONSE),
			createUuidReader(),
			createStringReader(),
			createAddressReader()
		);

		Optional<PayloadMessage> result = reader.read(null);
		assertThat(result).isPresent();

		PayloadMessage message = result.get();
		assertThat(message.getVersion()).isEqualTo(EXPECTED_VERSION);
		assertThat(message.getPriority()).isEqualTo(EXPECTED_PRIORITY);
		assertThat(message.getUuid()).isEqualTo(EXPECTED_UUID);
		assertThat(message.getType()).isEqualTo(MessageType.RESPONSE);
		assertThat(message.getContentType()).isEqualTo(EXPECTED_STRING);
		assertThat(message.getContent()).isEqualTo(EXPECTED_STRING);
		assertThat(message.getFrom()).isEqualTo(EXPECTED_ADDRESS);
		assertThat(message.getTo()).isEqualTo(EXPECTED_ADDRESS);
	}

	private TestUuidReader createUuidReader(){
		TestUuidReader reader = Mockito.mock(TestUuidReader.class);
		Mockito
			.when(reader.read(Mockito.anyObject()))
			.thenReturn(EXPECTED_UUID);
		return reader;
	}

	private TestStringReader createStringReader(){
		TestStringReader reader = Mockito.mock(TestStringReader.class);
		Mockito
			.when(reader.read(Mockito.anyObject()))
			.thenReturn(EXPECTED_STRING);
		return reader;
	}

	private TestAddressReader createAddressReader(){
		TestAddressReader reader = Mockito.mock(TestAddressReader.class);
		Mockito
			.when(reader.read(Mockito.anyObject()))
			.thenReturn(EXPECTED_ADDRESS);
		return reader;
	}

	private static class TestHeaderReader implements ByteBufferValueReader<Integer[]>{
		private boolean doThrow;
		private int version;
		private int priority;
		private MessageType type;

		public TestHeaderReader(boolean doThrow) {
			this.doThrow = doThrow;
		}

		public TestHeaderReader(int version, int priority, MessageType type) {
			this.version = version;
			this.priority = priority;
			this.type = type;
		}

		@Override
		public Integer[] read(ByteBuf buffer) {
			if (doThrow){
				throw new RuntimeException("");
			}

			Integer[] r = new Integer[3];
			r[0] = version;
			r[1] = priority;
			r[2] = type.getValue();
			return r;
		}
	}
	private static abstract class TestUuidReader implements ByteBufferValueReader<UUID>{}
	private static abstract class TestStringReader implements ByteBufferValueReader<String>{}
	private static abstract class TestAddressReader implements ByteBufferValueReader<Address>{}
}
