package ru.cwe.conversation.message.confirmation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.MessageType;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ConfirmationMessageImplTest {
	private static final int EXPECTED_VERSION = 0;
	private static final UUID EXPECTED_UUID = UUID.randomUUID();
	private static final ConfirmationResult EXPECTED_RESULT = ConfirmationResult.RESPONSE;
	private static final String EXPECTED_PAYLOAD_MESSAGE_TYPE = "some.payload.type";

	private static ConfirmationMessageImpl message;

	@BeforeAll
	static void beforeAll() {
		message = new ConfirmationMessageImpl(EXPECTED_VERSION, EXPECTED_UUID, EXPECTED_RESULT, EXPECTED_PAYLOAD_MESSAGE_TYPE);
	}

	@Test
	void shouldCheckVersionGetting() {
		assertThat(message.getVersion()).isEqualTo(EXPECTED_VERSION);
	}

	@Test
	void shouldCheckUuidGetting() {
		assertThat(message.getUuid()).isEqualTo(EXPECTED_UUID);
	}

	@Test
	void shouldCheckTypeGetting() {
		assertThat(message.getType()).isEqualTo(MessageType.CONFIRMATION);
	}

	@Test
	void shouldCheckResultGetting() {
		assertThat(message.getResult()).isEqualTo(EXPECTED_RESULT);
	}

	@Test
	void shouldCheckPayloadTypeGetting() {
		assertThat(message.getPayloadMessageType()).isEqualTo(EXPECTED_PAYLOAD_MESSAGE_TYPE);
	}
}
