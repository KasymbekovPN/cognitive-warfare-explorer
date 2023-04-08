package ru.cwe.conversation.writer.buffer;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.writer.value.ByteBufferValueWriter;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PayloadByteBufferWriterTest {

	@Test
	void shouldCheckWriting() {
		int expectedVersion = Fakers.message().version();
		int expectedPriority = Fakers.message().priority();
		MessageType expectedType = Fakers.message().messageType();
		UUID expectedUuid = Fakers.base().uuid().uuid();
		String expectedContentType = Fakers.base().string().string();
		String expectedContent = Fakers.base().string().string();
		Address expectedFrom = Fakers.address().address();
		Address expectedTo = Fakers.address().address();
		TestPayloadMessage message = new TestPayloadMessage(
			expectedVersion,
			expectedPriority,
			expectedType,
			expectedUuid,
			expectedContentType,
			expectedContent,
			expectedFrom,
			expectedTo
		);

		TestHeaderWriter headersWriter = new TestHeaderWriter();
		TestUuidWriter uuidWriter = new TestUuidWriter();
		TestStringWriter stringWriter = new TestStringWriter();
		TestAddressWriter addressWriter = new TestAddressWriter();
		PayloadByteBufferWriter.builder()
			.header(headersWriter)
			.uuid(uuidWriter)
			.string(stringWriter)
			.address(addressWriter)
			.build()
			.write(null, message);

		assertThat(headersWriter.getVersion()).isEqualTo(expectedVersion);
		assertThat(headersWriter.getPriority()).isEqualTo(expectedPriority);
		assertThat(headersWriter.getType()).isEqualTo(expectedType);
		assertThat(uuidWriter.getUuid()).isEqualTo(expectedUuid);
		assertThat(stringWriter.getStrings()).isEqualTo(List.of(expectedContentType, expectedContent));
		assertThat(addressWriter.getAddresses()).isEqualTo(List.of(expectedFrom, expectedTo));
	}

	@Getter
	private static class TestHeaderWriter implements ByteBufferValueWriter<Integer[]> {
		private int version;
		private int priority;
		private MessageType type;

		@Override
		public void write(ByteBuf buffer, Integer[] element) {
			version = element[0];
			priority = element[1];
			type = MessageType.valueOf(element[2]);
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
		private final List<String> strings = new ArrayList<>();
		@Override
		public void write(ByteBuf buffer, String element) {
			this.strings.add(element);
		}
	}

	@Getter
	private static class TestAddressWriter implements ByteBufferValueWriter<Address>{
		private final List<Address> addresses = new ArrayList<>();

		@Override
		public void write(ByteBuf buffer, Address element) {
			this.addresses.add(element);
		}
	}
}
