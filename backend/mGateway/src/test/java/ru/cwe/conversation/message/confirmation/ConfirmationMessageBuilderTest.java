package ru.cwe.conversation.message.confirmation;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.Priorities;
import ru.cwe.utils.reflection.Reflections;
import utils.TestPayloadMessage;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ConfirmationMessageBuilderTest {

	@SneakyThrows
	@Test
	void shouldCheckVersion() {
		int expectedVersion = 0;
		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder().version(expectedVersion);
		int version = Reflections.get(builder, "version", Integer.class);

		assertThat(version).isEqualTo(expectedVersion);
	}

	@SneakyThrows
	@Test
	void shouldCheckPriority() {
		int expectedPriority = 123;
		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder().priority(expectedPriority);
		int priority = Reflections.get(builder, "priority", Integer.class);

		assertThat(priority).isEqualTo(expectedPriority);
	}

	@SneakyThrows
	@Test
	void shouldCheckUuid() {
		UUID expectedUuid = UUID.randomUUID();
		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder().uuid(expectedUuid);
		UUID uuid = Reflections.get(builder, "uuid", UUID.class);

		assertThat(uuid).isEqualTo(expectedUuid);
	}

	@SneakyThrows
	@Test
	void shouldCheckResult() {
		ConfirmationResult expectedResult = ConfirmationResult.RESPONSE;
		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder().result(expectedResult);
		ConfirmationResult result = Reflections.get(builder, "result", ConfirmationResult.class);

		assertThat(result).isEqualTo(expectedResult);
	}

	@SneakyThrows
	@Test
	void shouldCheckPayloadMessageType() {
		String expectedType = "some.payload.message.type";
		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder().payloadMessageType(expectedType);
		String payloadMessageType = Reflections.get(builder, "payloadMessageType", String.class);

		assertThat(payloadMessageType).isEqualTo(expectedType);
	}

	@SneakyThrows
	@Test
	void shouldCheckFromPayloadMessage_ifItIsRequest() {
		UUID expectedUuid = UUID.randomUUID();
		MessageType type = MessageType.REQUEST;
		int expectedVersion = 0;
		int expectedPriority = 123;
		TestPayloadMessage payloadMessage = new TestPayloadMessage(
			expectedVersion,
			expectedPriority,
			type,
			expectedUuid,
			null,
			null,
			null,
			null
		);

		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder().fromPayloadMessage(payloadMessage);
		int version = Reflections.get(builder, "version", Integer.class);
		Integer priority = Reflections.get(builder, "priority", Integer.class);
		UUID uuid = Reflections.get(builder, "uuid", UUID.class);
		ConfirmationResult result = Reflections.get(builder, "result", ConfirmationResult.class);

		assertThat(version).isEqualTo(expectedVersion);
		assertThat(priority).isEqualTo(expectedPriority);
		assertThat(uuid).isEqualTo(expectedUuid);
		assertThat(result).isEqualTo(ConfirmationResult.REQUEST);
	}

	@SneakyThrows
	@Test
	void shouldCheckFromPayloadMessage_ifItIsResponse() {
		UUID expectedUuid = UUID.randomUUID();
		MessageType type = MessageType.RESPONSE;
		int expectedVersion = 0;
		int expectedPriority = 123;
		TestPayloadMessage payloadMessage = new TestPayloadMessage(
			expectedVersion,
			expectedPriority,
			type,
			expectedUuid,
			null,
			null,
			null,
			null
		);

		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder().fromPayloadMessage(payloadMessage);
		int version = Reflections.get(builder, "version", Integer.class);
		Integer priority = Reflections.get(builder, "priority", Integer.class);
		UUID uuid = Reflections.get(builder, "uuid", UUID.class);
		ConfirmationResult result = Reflections.get(builder, "result", ConfirmationResult.class);

		assertThat(version).isEqualTo(expectedVersion);
		assertThat(priority).isEqualTo(expectedPriority);
		assertThat(uuid).isEqualTo(expectedUuid);
		assertThat(result).isEqualTo(ConfirmationResult.RESPONSE);
	}

	@SneakyThrows
	@Test
	void shouldCheckError() {
		Object object = new Object();
		String expectedPayloadMessageType = object.getClass().getSimpleName();
		UUID expectedUuid = new UUID(0, 0);

		ConfirmationMessageBuilder builder = ConfirmationMessageBuilder.builder().error(object);

		Integer priority = Reflections.get(builder, "priority", Integer.class);
		UUID uuid = Reflections.get(builder, "uuid", UUID.class);
		String payloadMessageType = Reflections.get(builder, "payloadMessageType", String.class);

		assertThat(priority).isEqualTo(Priorities.MAX);
		assertThat(uuid).isEqualTo(expectedUuid);
		assertThat(payloadMessageType).isEqualTo(expectedPayloadMessageType);
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
		int version = 123;
		int expectedPriority = 100;
		UUID expectedUuid = UUID.randomUUID();
		ConfirmationResult expectedResult = ConfirmationResult.REQUEST;
		String expectedPayloadMessageType = "some.payload.message.type";
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
		UUID expectedUuid = UUID.randomUUID();
		int version = 123;
		int priority = 100;
		ConfirmationResult expectedResult = ConfirmationResult.REQUEST;
		String expectedPayloadMessageType = "some.payload.message.type";
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
