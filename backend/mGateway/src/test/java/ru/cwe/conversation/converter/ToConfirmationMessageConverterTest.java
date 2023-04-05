package ru.cwe.conversation.converter;

import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import utils.TestConfirmationMessage;
import utils.faker.Fakers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ToConfirmationMessageConverterTest {

	@Test
	void shouldCheckConversion_ifInvalidType() {
		Optional<ConfirmationMessage> maybeMessage = new ToConfirmationMessageConverter().apply(new Object());
		assertThat(maybeMessage).isEmpty();
	}

	@Test
	void shouldCheckConversion() {
		TestConfirmationMessage message = new TestConfirmationMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			Fakers.base().uuid().uuid(),
			Fakers.message().confirmationResult(),
			Fakers.base().string().string()
		);

		Optional<ConfirmationMessage> maybeMessage = new ToConfirmationMessageConverter().apply(message);
		assertThat(maybeMessage).isPresent();
		assertThat(maybeMessage.get()).isEqualTo(message);
	}
}
