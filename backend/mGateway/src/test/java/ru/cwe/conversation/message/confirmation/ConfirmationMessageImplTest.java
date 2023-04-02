package ru.cwe.conversation.message.confirmation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.MessageType;
import utils.faker.Fakers;
import utils.faker.UuidFaker;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ConfirmationMessageImplTest {
	private static int expectedVersion;
	private static int expectedPriority;
	private static UUID expectedUuid;
	private static ConfirmationResult expectedResult;
	private static String expectedPayloadMessageType;

	private static ConfirmationMessageImpl message;

	@BeforeAll
	static void beforeAll() {
		expectedVersion = Fakers.message().version();
		expectedPriority = Fakers.message().priority();
		expectedUuid = Fakers.base().uuid().uuid();
		expectedResult = ConfirmationResult.RESPONSE;
		expectedPayloadMessageType = Fakers.base().string().string();
		message = new ConfirmationMessageImpl(
			expectedVersion,
			expectedPriority,
			expectedUuid,
			expectedResult,
			expectedPayloadMessageType
		);
	}

	@Test
	void shouldCheckVersionGetting() {
		assertThat(message.getVersion()).isEqualTo(expectedVersion);
	}

	@Test
	void shouldCheckPriorityGetting() {
		assertThat(message.getPriority()).isEqualTo(expectedPriority);
	}

	@Test
	void shouldCheckUuidGetting() {
		assertThat(message.getUuid()).isEqualTo(expectedUuid);
	}

	@Test
	void shouldCheckTypeGetting() {
		assertThat(message.getType()).isEqualTo(MessageType.CONFIRMATION);
	}

	@Test
	void shouldCheckResultGetting() {
		assertThat(message.getResult()).isEqualTo(expectedResult);
	}

	@Test
	void shouldCheckPayloadTypeGetting() {
		assertThat(message.getPayloadMessageType()).isEqualTo(expectedPayloadMessageType);
	}
}
