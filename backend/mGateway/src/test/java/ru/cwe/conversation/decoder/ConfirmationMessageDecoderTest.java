package ru.cwe.conversation.decoder;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;
import ru.cwe.conversation.reader.buffer.ByteBufferReader;
import utils.TestConfirmationMessage;
import utils.faker.Fakers;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ConfirmationMessageDecoderTest {

	@SneakyThrows
	@Test
	void shouldCheckDecoding_ifInvalidMessage() {
		ArrayList<Object> out = new ArrayList<>();
		ConfirmationMessageDecoder.builder()
			.confirmation(new TestReader<>())
			.build()
			.decode(null, null, out);

		assertThat(out).isEmpty();
	}

	@SneakyThrows
	@Test
	void shouldCheckDecoding_ifConfirmationMessage() {
		TestConfirmationMessage message = new TestConfirmationMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			Fakers.base().uuid().uuid(),
			ConfirmationResult.REQUEST,
			Fakers.base().string().string()
		);

		ArrayList<Object> out = new ArrayList<>();
		ConfirmationMessageDecoder.builder()
			.confirmation(new TestReader<>(message))
			.build()
			.decode(null, null, out);

		assertThat(out.size()).isEqualTo(1);
		assertThat(out.get(0)).isEqualTo(message);
	}

	@Getter
	private static class TestReader<M> implements ByteBufferReader<M> {
		private M message;

		public TestReader(M message) {
			this.message = message;
		}

		public TestReader() {
		}

		@Override
		public Optional<M> read(ByteBuf buffer) {
			return message != null ? Optional.of(message) :  Optional.empty();
		}
	}

}
