package ru.cwe.conversation.message;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.type.MessageType;
import utils.TestAddress;
import utils.TestMessageType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class MessageBuilderTest {

	private static final MessageType EXPECTED_TYPE = new TestMessageType("some.message.type");
	private static final String EXPECTED_CONTENT = "some.content";
	private static final Address EXPECTED_FROM = new TestAddress("some.from", 0);
	private static final Address EXPECTED_TO = new TestAddress("some.to", 0);

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckFailMessageBuilding.csv")
	void shouldCheckFailMessageBuilding(String type, String content, String from, String to, String expectedMessage) {
		MessageBuilder builder = new MessageBuilder();
		if (type != null){
			builder.type(new TestMessageType(type));
		}
		if (content != null){
			builder.content(content);
		}
		if (from != null){
			builder.from(new TestAddress(from, 0));
		}
		if (to != null){
			builder.to(new TestAddress(to, 0));
		}
		Throwable throwable = catchThrowable(() -> {
			Message build = builder.build();
		});

		assertThat(throwable)
			.isInstanceOf(MessageBuildingRuntimeException.class)
			.hasMessage(expectedMessage);
	}

	@Test
	void shouldCheckMessageBuilding_ifItRequest() {
		Message message = new MessageBuilder()
			.type(EXPECTED_TYPE)
			.content(EXPECTED_CONTENT)
			.from(EXPECTED_FROM)
			.to(EXPECTED_TO)
			.build();
		assertThat(message.isResponse()).isFalse();
		assertThat(message.getType()).isEqualTo(EXPECTED_TYPE);
		assertThat(message.getContent()).isEqualTo(EXPECTED_CONTENT);
		assertThat(message.getFrom()).isEqualTo(EXPECTED_FROM);
		assertThat(message.getTo()).isEqualTo(EXPECTED_TO);
	}

	@Test
	void shouldCheckResponseBuilding() {
		Message message = new MessageBuilder()
			.response()
			.type(EXPECTED_TYPE)
			.content(EXPECTED_CONTENT)
			.from(EXPECTED_FROM)
			.to(EXPECTED_TO)
			.build();
		assertThat(message.isResponse()).isTrue();
		assertThat(message.getType()).isEqualTo(EXPECTED_TYPE);
		assertThat(message.getContent()).isEqualTo(EXPECTED_CONTENT);
		assertThat(message.getFrom()).isEqualTo(EXPECTED_FROM);
		assertThat(message.getTo()).isEqualTo(EXPECTED_TO);
	}
}
