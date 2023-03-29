package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.address.AddressBuilder;
import ru.cwe.conversation.address.AddressBuildingRuntimeException;
import utils.TestBuffers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AddressByteBufferValueReaderTest {

	@Test
	void shouldCheckReading_ifFail() {
		ByteBufferValueReader<String> reader = new ByteBufferValueReader<>() {
			@Override
			public String read(ByteBuf buffer) {
				throw new IndexOutOfBoundsException("");
			}
		};

		Throwable throwable = catchThrowable(() -> {
			new AddressByteBufferValueReader(reader, null).read(
				TestBuffers.create()
			);
		});

		assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
	}

	@Test
	void shouldCheckReading_ifHostIsNull() {
		Throwable throwable = catchThrowable(() -> {
			AddressByteBufferValueReader reader = new AddressByteBufferValueReader(
				new TestStringReader(),
				AddressBuilder.builder()
			);
			reader.read(createBuffer(null, 0));
		});

		assertThat(throwable).isInstanceOf(AddressBuildingRuntimeException.class);
	}

	@Test
	void shouldCheckReading_ifPortLessThenMin() {
		Throwable throwable = catchThrowable(() -> {
			AddressByteBufferValueReader reader = new AddressByteBufferValueReader(
				new TestStringReader(),
				AddressBuilder.builder()
			);
			reader.read(createBuffer("host", -1));
		});

		assertThat(throwable).isInstanceOf(AddressBuildingRuntimeException.class);
	}

	@Test
	void shouldCheckReading_ifPortMpo0reThenMax() {
		Throwable throwable = catchThrowable(() -> {
			AddressByteBufferValueReader reader = new AddressByteBufferValueReader(
				new TestStringReader(),
				AddressBuilder.builder()
			);
			reader.read(createBuffer("host", 1_000_000));
		});

		assertThat(throwable).isInstanceOf(AddressBuildingRuntimeException.class);
	}

	@Test
	void shouldCheckReading() {
		String expectedHost = "host";
		int expectedPort = 8080;

		AddressByteBufferValueReader reader = new AddressByteBufferValueReader(
			new TestStringReader(),
			AddressBuilder.builder()
		);
		Address address = reader.read(createBuffer(expectedHost, expectedPort));

		assertThat(address.getHost()).isEqualTo(expectedHost);
		assertThat(address.getPort()).isEqualTo(expectedPort);
	}

	private ByteBuf createBuffer(String host, int port){
		ByteBuf buffer = TestBuffers.create();
		TestBuffers.writeString(buffer, host);
		TestBuffers.writeInt(buffer, port);

		return buffer;
	}

	private static class TestStringReader implements ByteBufferValueReader<String>{
		@Override
		public String read(ByteBuf buffer) {
			return TestBuffers.readString(buffer);
		}
	}
}
