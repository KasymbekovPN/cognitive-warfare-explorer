package ru.cwe.common.test.message;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import ru.cwe.common.address.Address;
import ru.cwe.common.message.MessageType;
import ru.cwe.common.test.faker.AddressFaker;
import ru.cwe.common.test.faker.BaseFaker;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TestPayloadMessageTest {
	private static final BaseFaker FAKER = new BaseFaker(new Faker());

	@RepeatedTest(1_000)
	void shouldCheckVersionGetting() {
		int expectedVersion = FAKER.integer().value();
		int version = new TestPayloadMessage(
			expectedVersion,
			0,
			null,
			null,
			null,
			null,
			null,
			null
		).getVersion();

		assertThat(version).isEqualTo(expectedVersion);
	}

	@RepeatedTest(1_000)
	void shouldCheckPriorityGetting() {
		int expectedPriority = FAKER.integer().value();
		int priority = new TestPayloadMessage(
			0,
			expectedPriority,
			null,
			null,
			null,
			null,
			null,
			null
		).getPriority();

		assertThat(priority).isEqualTo(expectedPriority);
	}

	@RepeatedTest(10)
	void shouldCheckTypeGetting() {
		MessageType expectedType = MessageType.valueOf(FAKER.integer().between(0, 4));
		MessageType type = new TestPayloadMessage(
			0,
			0,
			expectedType,
			null,
			null,
			null,
			null,
			null
		).getType();

		assertThat(type).isEqualTo(expectedType);
	}

	@RepeatedTest(1_000)
	void shouldCheckUuidGetting() {
		UUID expectedUuid = FAKER.uuid().uuid();
		UUID uuid = new TestPayloadMessage(
			0,
			0,
			null,
			expectedUuid,
			null,
			null,
			null,
			null
		).getUuid();

		assertThat(uuid).isEqualTo(expectedUuid);
	}

	@RepeatedTest(1_000)
	void shouldCheckContentTypeGetting() {
		String expectedContentType = FAKER.string().string();
		String contentType = new TestPayloadMessage(
			0,
			0,
			null,
			null,
			expectedContentType,
			null,
			null,
			null
		).getContentType();

		assertThat(contentType).isEqualTo(expectedContentType);
	}

	@RepeatedTest(1_000)
	void shouldCheckContentGetting() {
		String expectedContent = FAKER.string().string();
		String content = new TestPayloadMessage(
			0,
			0,
			null,
			null,
			null,
			expectedContent,
			null,
			null
		).getContent();

		assertThat(content).isEqualTo(expectedContent);
	}

	@RepeatedTest(1_000)
	void shouldCheckFromGetting() {
		Address expectedFrom = new AddressFaker(FAKER).address();
		Address from = new TestPayloadMessage(
			0,
			0,
			null,
			null,
			null,
			null,
			expectedFrom,
			null
		).getFrom();

		assertThat(from).isEqualTo(expectedFrom);
	}

	@RepeatedTest(1_000)
	void shouldCheckToGetting() {
		Address expectedTo = new AddressFaker(FAKER).address();
		Address to = new TestPayloadMessage(
			0,
			0,
			null,
			null,
			null,
			null,
			null,
			expectedTo
		).getTo();

		assertThat(to).isEqualTo(expectedTo);
	}
}
