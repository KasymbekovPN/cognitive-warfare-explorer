package ru.cwe.conversation.filter;

import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.MessageType;
import utils.TestConfirmationMessage;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import static org.assertj.core.api.Assertions.assertThat;

class ConfirmationMessageFilterTest {

	@Test
	void shouldCheckFilter_ifNotConfirmationMessage() {
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
		boolean result = new ConfirmationMessageFilter().filter(message);

		assertThat(result).isFalse();
	}

	@Test
	void shouldCheckFilter() {
		TestConfirmationMessage message = new TestConfirmationMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			Fakers.base().uuid().uuid(),
			Fakers.message().confirmationResult(),
			Fakers.base().string().string()
		);
		boolean result = new ConfirmationMessageFilter().filter(message);

		assertThat(result).isTrue();
	}
}