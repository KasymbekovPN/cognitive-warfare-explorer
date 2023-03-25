package ru.cwe.conversation.message.confirmation;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.utils.reflection.Reflections;
import utils.TestPayloadMessage;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ConfirmationMessageBuilderTest {

	@SneakyThrows
	@Test
	void shouldCheckUuid() {
		UUID expectedUuid = UUID.randomUUID();
		ConfirmationMessageBuilder builder = new ConfirmationMessageBuilder().uuid(expectedUuid);
		UUID uuid = Reflections.get(builder, "uuid", UUID.class);

		assertThat(uuid).isEqualTo(expectedUuid);
	}

	@SneakyThrows
	@Test
	void shouldCheckResult() {
		ConfirmationResult expectedResult = ConfirmationResult.RESPONSE;
		ConfirmationMessageBuilder builder = new ConfirmationMessageBuilder().result(expectedResult);
		ConfirmationResult result = Reflections.get(builder, "result", ConfirmationResult.class);

		assertThat(result).isEqualTo(expectedResult);
	}

	@SneakyThrows
	@Test
	void shouldCheckPayloadMessageType() {
		String expectedType = "some.payload.message.type";
		ConfirmationMessageBuilder builder = new ConfirmationMessageBuilder().payloadMessageType(expectedType);
		String payloadMessageType = Reflections.get(builder, "payloadMessageType", String.class);

		assertThat(payloadMessageType).isEqualTo(expectedType);
	}

	@SneakyThrows
	@Test
	void shouldCheckFromPayloadMessage_ifItIsRequest() {
		UUID expectedUuid = UUID.randomUUID();
		MessageType type = MessageType.REQUEST;
		TestPayloadMessage payloadMessage = new TestPayloadMessage(expectedUuid, type, null, null, null, null);

		ConfirmationMessageBuilder builder = new ConfirmationMessageBuilder().fromPayloadMessage(payloadMessage);
		UUID uuid = Reflections.get(builder, "uuid", UUID.class);
		ConfirmationResult result = Reflections.get(builder, "result", ConfirmationResult.class);

		assertThat(uuid).isEqualTo(expectedUuid);
		assertThat(result).isEqualTo(ConfirmationResult.REQUEST);
	}

	@SneakyThrows
	@Test
	void shouldCheckFromPayloadMessage_ifItIsResponse() {
		UUID expectedUuid = UUID.randomUUID();
		MessageType type = MessageType.RESPONSE;
		TestPayloadMessage payloadMessage = new TestPayloadMessage(expectedUuid, type, null, null, null, null);

		ConfirmationMessageBuilder builder = new ConfirmationMessageBuilder().fromPayloadMessage(payloadMessage);
		UUID uuid = Reflections.get(builder, "uuid", UUID.class);
		ConfirmationResult result = Reflections.get(builder, "result", ConfirmationResult.class);

		assertThat(uuid).isEqualTo(expectedUuid);
		assertThat(result).isEqualTo(ConfirmationResult.RESPONSE);
	}

	@SneakyThrows
	@Test
	void shouldCheckError() {
		Object object = new Object();
		String expectedPayloadMessageType = object.getClass().getSimpleName();
		UUID expectedUuid = new UUID(0, 0);

		ConfirmationMessageBuilder builder = new ConfirmationMessageBuilder().error(object);

		UUID uuid = Reflections.get(builder, "uuid", UUID.class);
		assertThat(uuid).isEqualTo(expectedUuid);

		String payloadMessageType = Reflections.get(builder, "payloadMessageType", String.class);
		assertThat(payloadMessageType).isEqualTo(expectedPayloadMessageType);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckFailBuildingAttempt.csv")
	void shouldCheckFailBuildingAttempt(Long uuidSource, Integer resultSource, String expectedMessage) {
		Throwable throwable = catchThrowable(() -> {
			ConfirmationMessageBuilder builder = new ConfirmationMessageBuilder();
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
		UUID expectedUuid = UUID.randomUUID();
		ConfirmationResult expectedResult = ConfirmationResult.REQUEST;
		String expectedPayloadMessageType = "some.payload.message.type";
		ConfirmationMessage message = new ConfirmationMessageBuilder()
			.uuid(expectedUuid)
			.result(expectedResult)
			.payloadMessageType(expectedPayloadMessageType)
			.build();

		assertThat(message.getUuid()).isEqualTo(expectedUuid);
		assertThat(message.getResult()).isEqualTo(expectedResult);
		assertThat(message.getPayloadMessageType()).isEqualTo(expectedPayloadMessageType);
	}
}
