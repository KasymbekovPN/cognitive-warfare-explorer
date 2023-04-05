package ru.cwe.conversation.converter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PayloadToConfirmationMessageConverterTest {

	@Test
	void shouldCheckConversion() {
		int expectedVersion = Fakers.message().version();
		int expectedPriority = Fakers.message().priority();
		UUID expectedUuid = Fakers.base().uuid().uuid();
		TestPayloadMessage message = new TestPayloadMessage(
			expectedVersion,
			expectedPriority,
			MessageType.REQUEST,
			expectedUuid,
			Fakers.base().string().string(),
			Fakers.base().string().string(),
			Fakers.address().address(),
			Fakers.address().address()
		);

		ConfirmationMessage confirmationMessage = new PayloadToConfirmationMessageConverter().apply(message);
		assertThat(confirmationMessage.getVersion()).isEqualTo(expectedVersion);
		assertThat(confirmationMessage.getPriority()).isEqualTo(expectedPriority);
		assertThat(confirmationMessage.getResult()).isEqualTo(ConfirmationResult.REQUEST);
		assertThat(confirmationMessage.getUuid()).isEqualTo(expectedUuid);
	}
}
