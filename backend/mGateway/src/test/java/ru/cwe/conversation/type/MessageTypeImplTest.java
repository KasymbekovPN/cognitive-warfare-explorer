package ru.cwe.conversation.type;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageTypeImplTest {

	@Test
	void shouldCheckTypeGetting() {
		String expected = "some.type";
		String type = new MessageTypeImpl(expected).getName();

		assertThat(type).isEqualTo(expected);
	}
}
