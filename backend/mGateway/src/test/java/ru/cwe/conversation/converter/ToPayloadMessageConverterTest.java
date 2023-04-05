package ru.cwe.conversation.converter;

import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.payload.PayloadMessage;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ToPayloadMessageConverterTest {

	@Test
	void shouldCheckConversion_ifInvalidType() {
		Optional<PayloadMessage> maybe = new ToPayloadMessageConverter().apply(new Object());
		assertThat(maybe).isEmpty();
	}

	@Test
	void shouldCheckConversion() {
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
		Optional<PayloadMessage> maybe = new ToPayloadMessageConverter().apply(message);

		assertThat(maybe).isPresent();
		assertThat(maybe.get()).isEqualTo(message);
	}
}
