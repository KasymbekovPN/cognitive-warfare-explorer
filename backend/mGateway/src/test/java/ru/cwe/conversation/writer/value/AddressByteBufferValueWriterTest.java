package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import utils.BufferUtil;
import utils.TestAddress;

import static org.assertj.core.api.Assertions.assertThat;

class AddressByteBufferValueWriterTest {

	@Test
	void shouldCheckWriting() {
		String expectedHost = "host";
		int expectedPort = 8080;

		TestHostWriter hostWriter = new TestHostWriter();
		ByteBuf buffer = BufferUtil.create();
		new AddressByteBufferValueWriter(hostWriter).write(buffer, new TestAddress(expectedHost, expectedPort));

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
