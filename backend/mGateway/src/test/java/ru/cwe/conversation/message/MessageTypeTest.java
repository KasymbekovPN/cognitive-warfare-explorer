 package ru.cwe.conversation.message;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

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

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckValueOfFromInt.csv")
	void shouldCheckValueOfFromInt(int intSource, String stringSource) {
		assertThat(MessageType.valueOf(intSource)).isEqualTo(MessageType.valueOf(stringSource));
	}
}
