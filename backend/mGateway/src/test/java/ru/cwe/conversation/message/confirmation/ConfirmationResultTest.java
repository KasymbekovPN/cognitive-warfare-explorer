package ru.cwe.conversation.message.confirmation;

import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;

import static org.assertj.core.api.Assertions.assertThat;

class ConfirmationResultTest {

	@Test
	void shouldCheckRequestConfirmationResultGetting() {
		assertThat(ConfirmationResult.REQUEST.getValue()).isEqualTo(0);
	}

	@Test
	void shouldCheckResponseConfirmationResultGetting() {
		assertThat(ConfirmationResult.RESPONSE.getValue()).isEqualTo(1);
	}

	@Test
	void shouldCheckInvalidConfirmationResultGetting() {
		assertThat(ConfirmationResult.INVALID.getValue()).isEqualTo(2);
	}
}
