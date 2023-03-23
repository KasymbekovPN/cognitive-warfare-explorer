package ru.cwe.conversation.message.confirmation;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ConfirmationMessageBuilderTest {

	@SneakyThrows
	@Test
	void shouldCheckUuid() {
		UUID expectedUuid = UUID.randomUUID();
		ConfirmationMessageBuilder builder = new ConfirmationMessageBuilder().uuid(expectedUuid);
		Object result = getFieldValue(builder, "uuid");

		assertThat(result.getClass()).isEqualTo(UUID.class);
		UUID castResult = (UUID) result;
		assertThat(castResult).isEqualTo(expectedUuid);
	}

	@Test
	void shouldCheckResult() {

	}

	@Test
	void shouldCheckPayloadMessage() {

	}

	@Test
	void shouldCheckFromPayloadMessage_ifItIsRequest() {

	}

	@Test
	void shouldCheckFromPayloadMessage_ifItIsResponse() {

	}

	@Test
	void shouldCheckError() {

	}

	private Object getFieldValue(ConfirmationMessageBuilder builder, String name) throws NoSuchFieldException, IllegalAccessException {
		Field field = ConfirmationMessageBuilder.class.getDeclaredField(name);
		field.setAccessible(true);
		return field.get(builder);
	}
}
