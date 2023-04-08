package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.address.AddressBuildingRuntimeException;
import utils.BufferUtil;
import utils.faker.Fakers;

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
			AddressByteBufferValueReader.builder().string(reader).build().read(
				BufferUtil.create()
			);
		});

		assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
	}

	@Test
	void shouldCheckReading_ifHostIsNull() {
		Throwable throwable = catchThrowable(() -> {
			AddressByteBufferValueReader reader = AddressByteBufferValueReader.builder()
				.string(new TestStringReader())
				.build();
			reader.read(createBuffer(null, Fakers.address().port()));
		});

		assertThat(throwable).isInstanceOf(AddressBuildingRuntimeException.class);
	}

	@Test
	void shouldCheckReading_ifPortLessThenMin() {
		Throwable throwable = catchThrowable(() -> {
			AddressByteBufferValueReader reader = AddressByteBufferValueReader.builder()
				.string(new TestStringReader())
				.build();
			reader.read(createBuffer(Fakers.address().host(), Fakers.base().number().lessThan(0)));
		});

		assertThat(throwable).isInstanceOf(AddressBuildingRuntimeException.class);
	}

	@Test
	void shouldCheckReading_ifPortMpo0reThenMax() {
		Throwable throwable = catchThrowable(() -> {
			AddressByteBufferValueReader reader = AddressByteBufferValueReader.builder()
				.string(new TestStringReader())
				.build();
			reader.read(createBuffer(Fakers.address().host(), Fakers.base().number().moreThan(65535)));
		});

		assertThat(throwable).isInstanceOf(AddressBuildingRuntimeException.class);
	}

	@Test
	void shouldCheckReading() {
		String expectedHost = Fakers.address().host();
		int expectedPort = Fakers.address().port();

		AddressByteBufferValueReader reader = AddressByteBufferValueReader.builder()
			.string(new TestStringReader())
			.build();
		Address address = reader.read(createBuffer(expectedHost, expectedPort));

		assertThat(address.getHost()).isEqualTo(expectedHost);
		assertThat(address.getPort()).isEqualTo(expectedPort);
	}

	private ByteBuf createBuffer(String host, int port){
		ByteBuf buffer = BufferUtil.create();
		BufferUtil.writeString(buffer, host);
		BufferUtil.writeInt(buffer, port);

		return buffer;
	}

	private static class TestStringReader implements ByteBufferValueReader<String>{
		@Override
		public String read(ByteBuf buffer) {
			return BufferUtil.readString(buffer);
		}
	}
}
