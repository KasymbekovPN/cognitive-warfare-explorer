package ru.cwe.common.test.message;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import ru.cwe.common.message.MessageType;
import ru.cwe.common.message.confirmation.ConfirmationResult;
import ru.cwe.common.test.faker.BaseFaker;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TestConfirmationMessageTest {
	private static final BaseFaker FAKER = new BaseFaker(new Faker());

	@RepeatedTest(1_000)
	void shouldCheckVersionGetting() {
		int expectedVersion = FAKER.integer().value();
		int version = new TestConfirmationMessage(
			expectedVersion,
			0,
			null,
			null,
			null
		).getVersion();

		assertThat(version).isEqualTo(expectedVersion);
	}

	@RepeatedTest(1_000)
	void shouldCheckPriorityGetting() {
		int expectedPriority = FAKER.integer().value();
		int priority = new TestConfirmationMessage(
			0,
			expectedPriority,
			null,
			null,
			null
		).getPriority();

		assertThat(priority).isEqualTo(expectedPriority);
	}

	@Test
	void shouldCheckMessageTypeGetting() {
		MessageType type = new TestConfirmationMessage(
			0,
			0,
			null,
			null,
			null
		).getType();

		assertThat(type).isEqualTo(MessageType.CONFIRMATION);
	}

	@RepeatedTest(1_000)
	void shouldCheckUuidGetting() {
		UUID expectedUuid = UUID.randomUUID();
		UUID uuid = new TestConfirmationMessage(
			0,
			0,
			expectedUuid,
			null,
			null
		).getUuid();

		assertThat(uuid).isEqualTo(expectedUuid);
	}

	@RepeatedTest(10)
	void shouldCheckResultGetting() {
		ConfirmationResult expectedResult = ConfirmationResult.valueOf(FAKER.integer().between(0, 3));
		ConfirmationResult result = new TestConfirmationMessage(
			0,
			0,
			null,
			expectedResult,
			null
		).getResult();

		assertThat(result).isEqualTo(expectedResult);
	}

	@RepeatedTest(1_000)
	void shouldCheckPayloadMessageTypeGetting() {
		String expectedPayloadMessageType = FAKER.string().string();
		String payloadMessageType = new TestConfirmationMessage(
			0,
			0,
			null,
			null,
			expectedPayloadMessageType
		).getPayloadMessageType();

		assertThat(payloadMessageType).isEqualTo(expectedPayloadMessageType);
	}
}
