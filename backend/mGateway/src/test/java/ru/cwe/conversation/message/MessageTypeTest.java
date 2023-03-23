 package ru.cwe.conversation.message;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageTypeTest {

	@Test
	void shouldCheckRequestValueGetting() {
		assertThat(MessageType.REQUEST.getValue()).isEqualTo(0);
	}

	@Test
	void shouldCheckResponseValueGetting() {
		assertThat(MessageType.RESPONSE.getValue()).isEqualTo(1);
	}

	@Test
	void shouldCheckConfirmationValueGetting() {
		assertThat(MessageType.CONFIRMATION.getValue()).isEqualTo(2);
	}
}
