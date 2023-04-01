package ru.cwe.conversation.decoder;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.reader.buffer.ByteBufferReader;
import ru.cwe.conversation.message.Message;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;
import utils.TestAddress;
import utils.TestConfirmationMessage;
import utils.TestPayloadMessage;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MessageDecoderTest {

	@SneakyThrows
	@Test
	void shouldCheckDecoding_ifInvalidMessage() {
		ArrayList<Object> out = new ArrayList<>();
		new MessageDecoder(new TestReader(), new TestReader()).decode(null, null, out);

		assertThat(out).isEmpty();
	}

	@SneakyThrows
	@Test
	void shouldCheckDecoding_ifConfirmationMessage() {
		TestConfirmationMessage message
			= new TestConfirmationMessage(0, 0, UUID.randomUUID(), ConfirmationResult.REQUEST, "");

		ArrayList<Object> out = new ArrayList<>();
		new MessageDecoder(new TestReader(message), new TestReader()).decode(null, null, out);

		assertThat(out.size()).isEqualTo(1);
		assertThat(out.get(0)).isEqualTo(message);
	}

	@SneakyThrows
	@Test
	void shouldCheckDecoding_ifPayloadMessage() {
		TestPayloadMessage message = new TestPayloadMessage(
			0,
			0,
			MessageType.REQUEST,
			UUID.randomUUID(),
			"",
			"",
			new TestAddress(),
			new TestAddress()
		);

		ArrayList<Object> out = new ArrayList<>();
		new MessageDecoder(new TestReader(), new TestReader(message)).decode(null, null, out);

		assertThat(out.size()).isEqualTo(1);
		assertThat(out.get(0)).isEqualTo(message);
	}

	@Getter
	private static class TestReader implements ByteBufferReader<Message>{
		private Message message;

		public TestReader(Message message) {
			this.message = message;
		}

		public TestReader() {
		}

		@Override
		public Optional<Message> read(ByteBuf buffer) {
			return message != null ? Optional.of(message) :  Optional.empty();
		}
	}
}
