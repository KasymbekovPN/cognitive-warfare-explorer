package ru.cwe.conversation.reader.buffer;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.reader.value.ByteBufferValueReader;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.payload.PayloadMessage;
import utils.faker.Fakers;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PayloadByteBufferReaderTest {
	private int expectedVersion;
	private int expectedPriority;
	private UUID expectedUuid;
	private String expectedString;
	private Address expectedAddress;

	@BeforeEach
	void setUp() {
		expectedVersion = Fakers.message().version();
		expectedPriority = Fakers.message().priority();
		expectedUuid = Fakers.base().uuid().uuid();
		expectedString = Fakers.base().string().string();
		expectedAddress = Fakers.address().address();
	}

	@Test
	void shouldCheckReading_ifException() {
		PayloadByteBufferReader reader = PayloadByteBufferReader.builder()
			.header(new TestHeaderReader(true))
			.uuid(createUuidReader())
			.string(createStringReader())
			.address(createAddressReader())
			.build();

		Optional<PayloadMessage> result = reader.read(null);
		assertThat(result).isEmpty();
	}

	@Test
	void shouldCheckReading_ifTypeInvalid() {
		PayloadByteBufferReader reader = PayloadByteBufferReader.builder()
			.header(new TestHeaderReader(expectedVersion, expectedPriority, MessageType.INVALID))
			.uuid(createUuidReader())
			.string(createStringReader())
			.address(createAddressReader())
			.build();

		Optional<PayloadMessage> result = reader.read(null);
		assertThat(result).isEmpty();
	}

	@Test
	void shouldCheckReading_ifTypeConversation() {
		PayloadByteBufferReader reader = PayloadByteBufferReader.builder()
			.header(new TestHeaderReader(expectedVersion, expectedPriority, MessageType.CONFIRMATION))
			.uuid(createUuidReader())
			.string(createStringReader())
			.address(createAddressReader())
			.build();

		Optional<PayloadMessage> result = reader.read(null);
		assertThat(result).isEmpty();
	}

	@Test
	void shouldCheckReading_ifTypeRequest() {
		PayloadByteBufferReader reader = PayloadByteBufferReader.builder()
			.header(new TestHeaderReader(expectedVersion, expectedPriority, MessageType.REQUEST))
			.uuid(createUuidReader())
			.string(createStringReader())
			.address(createAddressReader())
			.build();

		Optional<PayloadMessage> result = reader.read(null);
		assertThat(result).isPresent();

		PayloadMessage message = result.get();
		assertThat(message.getVersion()).isEqualTo(expectedVersion);
		assertThat(message.getPriority()).isEqualTo(expectedPriority);
		assertThat(message.getUuid()).isEqualTo(expectedUuid);
		assertThat(message.getType()).isEqualTo(MessageType.REQUEST);
		assertThat(message.getContentType()).isEqualTo(expectedString);
		assertThat(message.getContent()).isEqualTo(expectedString);
		assertThat(message.getFrom()).isEqualTo(expectedAddress);
		assertThat(message.getTo()).isEqualTo(expectedAddress);
	}

	@Test
	void shouldCheckReading_ifTypeResponse() {
		PayloadByteBufferReader reader = PayloadByteBufferReader.builder()
			.header(new TestHeaderReader(expectedVersion, expectedPriority, MessageType.RESPONSE))
			.uuid(createUuidReader())
			.string(createStringReader())
			.address(createAddressReader())
			.build();

		Optional<PayloadMessage> result = reader.read(null);
		assertThat(result).isPresent();

		PayloadMessage message = result.get();
		assertThat(message.getVersion()).isEqualTo(expectedVersion);
		assertThat(message.getPriority()).isEqualTo(expectedPriority);
		assertThat(message.getUuid()).isEqualTo(expectedUuid);
		assertThat(message.getType()).isEqualTo(MessageType.RESPONSE);
		assertThat(message.getContentType()).isEqualTo(expectedString);
		assertThat(message.getContent()).isEqualTo(expectedString);
		assertThat(message.getFrom()).isEqualTo(expectedAddress);
		assertThat(message.getTo()).isEqualTo(expectedAddress);
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
			.thenReturn(expectedString);
		return reader;
	}

	private TestAddressReader createAddressReader(){
		TestAddressReader reader = Mockito.mock(TestAddressReader.class);
		Mockito
			.when(reader.read(Mockito.anyObject()))
			.thenReturn(expectedAddress);
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
