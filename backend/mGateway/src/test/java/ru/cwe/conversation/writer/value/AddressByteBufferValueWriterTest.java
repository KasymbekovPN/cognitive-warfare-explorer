package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import utils.BufferUtil;
import utils.TestAddress;
import utils.faker.Fakers;

import static org.assertj.core.api.Assertions.assertThat;

class AddressByteBufferValueWriterTest {

	@Test
	void shouldCheckWriting() {
		String expectedHost = Fakers.address().host();
		int expectedPort = Fakers.address().port();

		TestHostWriter hostWriter = new TestHostWriter();
		ByteBuf buffer = BufferUtil.create();
		AddressByteBufferValueWriter.builder().string(hostWriter).build()
			.write(buffer, new TestAddress(expectedHost, expectedPort));

		assertThat(hostWriter.getHost()).isEqualTo(expectedHost);
		assertThat(BufferUtil.readInt(buffer)).isEqualTo(expectedPort);
	}

	@Getter
	private static class TestHostWriter implements ByteBufferValueWriter<String>{
		private String host;

		@Override
		public void write(ByteBuf buffer, String element) {
			this.host = element;
		}
	}
}
