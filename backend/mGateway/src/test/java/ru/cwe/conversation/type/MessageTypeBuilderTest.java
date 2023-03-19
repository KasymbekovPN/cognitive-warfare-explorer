package ru.cwe.conversation.type;

import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.MessageBuildingRuntimeException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class MessageTypeBuilderTest {

	@Test
	void shouldCheckBuilding_ifTypeNull() {
		Throwable throwable = catchThrowable(() -> {
			new MessageTypeBuilder().build();
		});
		assertThat(throwable)
			.isInstanceOf(MessageBuildingRuntimeException.class)
			.hasMessage("Type is null");
	}

	@Test
	void shouldCheckBuilding_ifTypeIsBlank() {
		Throwable throwable = catchThrowable(() -> {
			new MessageTypeBuilder()
				.type("")
				.build();
		});
		assertThat(throwable)
			.isInstanceOf(MessageBuildingRuntimeException.class)
			.hasMessage("Type is wrong");
	}

	@Test
	void shouldCheckBuilding() {
		String expected = "some.type";
		MessageType messageType = new MessageTypeBuilder()
			.type(expected)
			.build();

		assertThat(messageType.getType()).isEqualTo(expected);
	}
}
