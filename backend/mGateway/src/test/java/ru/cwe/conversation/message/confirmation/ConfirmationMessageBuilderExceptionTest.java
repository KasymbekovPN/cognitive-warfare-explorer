package ru.cwe.conversation.message.confirmation;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ConfirmationMessageBuilderExceptionTest {

	@Test
	void shouldCheckBuilding_ifEmpty() {
		Optional<ConfirmationMessageBuilderException> maybeException
			= new ConfirmationMessageBuilderException.Builder().build();

		assertThat(maybeException).isEmpty();
	}

	@Test
	void shouldCheckBuilding_ifPresence() {
		String someField0 = "someField0";
		String someField1 = "someField1";
		String expectedMessage = String.format("Absent fields: %s & %s", someField0, someField1);

		Optional<ConfirmationMessageBuilderException> maybeException = new ConfirmationMessageBuilderException.Builder()
			.field(someField0, null)
			.field("abc", new Object())
			.field(someField1, null)
			.build();

		assertThat(maybeException).isPresent();
		assertThat(maybeException.get().getMessage()).isEqualTo(expectedMessage);
	}
}
