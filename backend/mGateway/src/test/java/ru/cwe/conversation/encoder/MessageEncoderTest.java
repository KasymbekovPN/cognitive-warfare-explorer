package ru.cwe.conversation.encoder;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.writer.buffer.ByteBufferWriter;
import ru.cwe.conversation.message.MessageType;
import utils.faker.Fakers;
import utils.TestConfirmationMessage;
import utils.TestPayloadMessage;

import static org.assertj.core.api.Assertions.assertThat;

class MessageEncoderTest {

	@SneakyThrows
	@Test
	void shouldCheckConfirmationMessageEncoding() {
		TestConfirmationMessage message = new TestConfirmationMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			Fakers.base().uuid().uuid(),
			Fakers.message().confirmationResult(),
			Fakers.base().string().string()
		);
		TestWriter<ConfirmationMessage> writer = new TestWriter<>();
		new MessageEncoder(writer, null).encode(null, message, null);

		assertThat(writer.getMessage()).isEqualTo(message);
	}

	@SneakyThrows
	@Test
	void shouldCheckRequestPayloadMessageEncoding() {
		TestPayloadMessage message = new TestPayloadMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			MessageType.REQUEST,
			Fakers.base().uuid().uuid(),
			Fakers.base().string().string(),
			Fakers.base().string().string(),
			Fakers.address().address(),
			Fakers.address().address()
		);
		TestWriter<PayloadMessage> writer = new TestWriter<>();
		new MessageEncoder(null, writer).encode(null, message, null);

		assertThat(writer.getMessage()).isEqualTo(message);
	}

	@SneakyThrows
	@Test
	void shouldCheckResponsePayloadMessageEncoding() {
		TestPayloadMessage message = new TestPayloadMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			MessageType.RESPONSE,
			Fakers.base().uuid().uuid(),
			Fakers.base().string().string(),
			Fakers.base().string().string(),
			Fakers.address().address(),
			Fakers.address().address()
		);
		TestWriter<PayloadMessage> writer = new TestWriter<>();
		new MessageEncoder(null, writer).encode(null, message, null);

		assertThat(writer.getMessage()).isEqualTo(message);
	}

	private static class TestWriter<M> implements ByteBufferWriter<M>{
		@Getter
		private M message;

		@Override
		public void write(ByteBuf buffer, M element) {
			this.message = element;
		}
	}
}
