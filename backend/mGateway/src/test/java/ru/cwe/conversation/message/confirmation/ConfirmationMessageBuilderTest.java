package ru.cwe.conversation.message.confirmation;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.Priorities;
import ru.cwe.utils.reflection.Reflections;
import utils.faker.Fakers;
import utils.TestPayloadMessage;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ConfirmationMessageBuilderTest {

	@SneakyThrows
	@Test
	void shouldCheckVersion() {
		int expectedVersion = Fakers.version();
		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder().version(expectedVersion);
		int version = Reflections.get(builder, "version", Integer.class);

		assertThat(version).isEqualTo(expectedVersion);
	}

	@SneakyThrows
	@Test
	void shouldCheckPriority() {
		int expectedPriority = Fakers.priority();
		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder().priority(expectedPriority);
		int priority = Reflections.get(builder, "priority", Integer.class);

		assertThat(priority).isEqualTo(expectedPriority);
	}

	@SneakyThrows
	@Test
	void shouldCheckUuid() {
		UUID expectedUuid = Fakers.uuid();
		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder().uuid(expectedUuid);
		UUID uuid = Reflections.get(builder, "uuid", UUID.class);

		assertThat(uuid).isEqualTo(expectedUuid);
	}

	@SneakyThrows
	@Test
	void shouldCheckResult() {
		ConfirmationResult expectedResult = Fakers.confirmationResult();
		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder().result(expectedResult);
		ConfirmationResult result = Reflections.get(builder, "result", ConfirmationResult.class);

		assertThat(result).isEqualTo(expectedResult);
	}

	@SneakyThrows
	@Test
	void shouldCheckPayloadMessageType() {
		String expectedType = Fakers.string();
		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder().payloadMessageType(expectedType);
		String payloadMessageType = Reflections.get(builder, "payloadMessageType", String.class);

		assertThat(payloadMessageType).isEqualTo(expectedType);
	}

	@SneakyThrows
	@Test
	void shouldCheckFromPayloadMessage_ifItIsRequest() {
		UUID expectedUuid = Fakers.uuid();
		MessageType type = MessageType.REQUEST;
		int expectedVersion = Fakers.version();
		int expectedPriority = Fakers.priority();
		TestPayloadMessage payloadMessage = new TestPayloadMessage(
			expectedVersion,
			expectedPriority,
			type,
			expectedUuid,
			Fakers.string(),
			Fakers.string(),
			Fakers.addressOld(),
			Fakers.addressOld()
		);

		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder().fromPayloadMessage(payloadMessage);
		assertThat(Reflections.get(builder, "version", Integer.class)).isEqualTo(expectedVersion);
		assertThat(Reflections.get(builder, "priority", Integer.class)).isEqualTo(expectedPriority);
		assertThat(Reflections.get(builder, "uuid", UUID.class)).isEqualTo(expectedUuid);
		assertThat(Reflections.get(builder, "result", ConfirmationResult.class)).isEqualTo(ConfirmationResult.REQUEST);
	}

	@SneakyThrows
	@Test
	void shouldCheckFromPayloadMessage_ifItIsResponse() {
		UUID expectedUuid = Fakers.uuid();
		MessageType type = MessageType.RESPONSE;
		int expectedVersion = Fakers.version();
		int expectedPriority = Fakers.priority();
		TestPayloadMessage payloadMessage = new TestPayloadMessage(
			expectedVersion,
			expectedPriority,
			type,
			expectedUuid,
			Fakers.string(),
			Fakers.string(),
			Fakers.addressOld(),
			Fakers.addressOld()
		);

		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder().fromPayloadMessage(payloadMessage);
		assertThat(Reflections.get(builder, "version", Integer.class)).isEqualTo(expectedVersion);
		assertThat(Reflections.get(builder, "priority", Integer.class)).isEqualTo(expectedPriority);
		assertThat(Reflections.get(builder, "uuid", UUID.class)).isEqualTo(expectedUuid);
		assertThat(Reflections.get(builder, "result", ConfirmationResult.class)).isEqualTo(ConfirmationResult.RESPONSE);
	}

	@SneakyThrows
	@Test
	void shouldCheckError() {
		Object object = new Object();
		String expectedPayloadMessageType = object.getClass().getSimpleName();
		UUID expectedUuid = new UUID(0, 0);

		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder().error(object);
		assertThat(Reflections.get(builder, "priority", Integer.class)).isEqualTo(Priorities.MAX);
		assertThat(Reflections.get(builder, "uuid", UUID.class)).isEqualTo(expectedUuid);
		assertThat(Reflections.get(builder, "payloadMessageType", String.class)).isEqualTo(expectedPayloadMessageType);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckFailBuildingAttempt.csv")
	void shouldCheckFailBuildingAttempt(Integer version, Long uuidSource, Integer resultSource, String expectedMessage) {
		Throwable throwable = catchThrowable(() -> {
			ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder();
			if (version != null){
				builder.version(version);
			}
			if (uuidSource != null){
				builder.uuid(new UUID(0, uuidSource));
			}
			if (resultSource != null){
				builder.result(ConfirmationResult.valueOf(resultSource));
			}
			builder.build();
		});

		assertThat(throwable)
			.isInstanceOf(ConfirmationMessageBuilderException.class)
			.hasMessage(expectedMessage);
	}

	@Test
	void shouldCheckBuilding() {
		int version = Fakers.version();
		int expectedPriority = Fakers.priority();
		UUID expectedUuid = Fakers.uuid();
		ConfirmationResult expectedResult = ConfirmationResult.REQUEST;
		String expectedPayloadMessageType = Fakers.string();
		ConfirmationMessage message = ConfirmationMessageBuilder.builder()
			.version(version)
			.priority(expectedPriority)
			.uuid(expectedUuid)
			.result(expectedResult)
			.payloadMessageType(expectedPayloadMessageType)
			.build();

		assertThat(message.getVersion()).isEqualTo(version);
		assertThat(message.getPriority()).isEqualTo(expectedPriority);
		assertThat(message.getUuid()).isEqualTo(expectedUuid);
		assertThat(message.getResult()).isEqualTo(expectedResult);
		assertThat(message.getPayloadMessageType()).isEqualTo(expectedPayloadMessageType);
	}

	@SneakyThrows
	@Test
	void shouldCheckReset() {
		UUID expectedUuid = Fakers.uuid();
		int version = Fakers.version();
		int priority = Fakers.priority();
		ConfirmationResult expectedResult = ConfirmationResult.REQUEST;
		String expectedPayloadMessageType = Fakers.string();
		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder()
			.version(version)
			.priority(priority)
			.uuid(expectedUuid)
			.result(expectedResult)
			.payloadMessageType(expectedPayloadMessageType)
			.reset();

		assertThat(Reflections.get(builder, "version", Integer.class)).isNull();
		assertThat(Reflections.get(builder, "priority", Integer.class)).isZero();
		assertThat(Reflections.get(builder, "uuid", UUID.class)).isNull();
		assertThat(Reflections.get(builder, "result", ConfirmationResult.class)).isNull();
		assertThat(Reflections.get(builder, "payloadMessageType", String.class)).isEmpty();
	}
}
