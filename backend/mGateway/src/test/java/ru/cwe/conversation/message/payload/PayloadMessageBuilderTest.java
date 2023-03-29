package ru.cwe.conversation.message.payload;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.utils.reflection.Reflections;
import utils.TestAddress;
import utils.TestPayloadMessage;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class PayloadMessageBuilderTest {
	private static final UUID EXPECTED_UUID = UUID.randomUUID();
	private static final MessageType EXPECTED_TYPE = MessageType.RESPONSE;
	private static final String EXPECTED_CONTENT_TYPE = "some.content.type";
	private static final String EXPECTED_CONTENT = "some.content";
	private static final Address EXPECTED_FROM = new TestAddress("from.host", 8080);
	private static final Address EXPECTED_TO = new TestAddress("some.host", 8081);

	@SneakyThrows
	@Test
	void shouldCheckUuid() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder().uuid(EXPECTED_UUID);
		UUID uuid = Reflections.get(builder, "uuid", UUID.class);

		assertThat(uuid).isEqualTo(EXPECTED_UUID);
	}

	@SneakyThrows
	@Test
	void shouldCheckType() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder().type(EXPECTED_TYPE);
		MessageType type = Reflections.get(builder, "type", MessageType.class);

		assertThat(type).isEqualTo(EXPECTED_TYPE);
	}

	@SneakyThrows
	@Test
	void shouldCheckContentType() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder().contentType(EXPECTED_CONTENT_TYPE);
		String contentType = Reflections.get(builder, "contentType", String.class);

		assertThat(contentType).isEqualTo(EXPECTED_CONTENT_TYPE);
	}

	@SneakyThrows
	@Test
	void shouldCheckContent() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder().content(EXPECTED_CONTENT);
		String content = Reflections.get(builder, "content", String.class);

		assertThat(content).isEqualTo(EXPECTED_CONTENT);
	}

	@SneakyThrows
	@Test
	void shouldCheckFrom() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder().from(EXPECTED_FROM);
		Address from = Reflections.get(builder, "from", Address.class);

		assertThat(from).isEqualTo(EXPECTED_FROM);
	}

	@SneakyThrows
	@Test
	void shouldCheckTo() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder().to(EXPECTED_TO);
		Address to = Reflections.get(builder, "to", Address.class);

		assertThat(to).isEqualTo(EXPECTED_TO);
	}

	@SneakyThrows
	@Test
	void shouldCheckRequest() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder().request();
		Object uuid = Reflections.get(builder, "uuid");
		
		assertThat(uuid).isInstanceOf(UUID.class);
		assertThat(Reflections.get(builder, "type", MessageType.class)).isEqualTo(MessageType.REQUEST);
	}

	@SneakyThrows
	@Test
	void shouldCheckResponse() {
		TestPayloadMessage request = new TestPayloadMessage(
			EXPECTED_UUID,
			MessageType.REQUEST,
			EXPECTED_CONTENT_TYPE,
			EXPECTED_CONTENT,
			EXPECTED_FROM,
			EXPECTED_TO
		);
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder()
			.response(request);

		assertThat(Reflections.get(builder, "uuid", UUID.class)).isEqualTo(EXPECTED_UUID);
		assertThat(Reflections.get(builder, "type", MessageType.class)).isEqualTo(MessageType.RESPONSE);
		assertThat(Reflections.get(builder, "from", Address.class)).isEqualTo(EXPECTED_TO);
		assertThat(Reflections.get(builder, "to", Address.class)).isEqualTo(EXPECTED_FROM);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckFailBuilding.csv")
	void shouldCheckFailBuilding_ifSomeIsNull(Integer uuidSource,
											  String messageTypeSource,
											  String contentType,
											  String content,
											  String fromSource,
											  String toSource,
											  String expectedMessage) {
		Throwable throwable = catchThrowable(() -> {
			PayloadMessageBuilder builder = PayloadMessageBuilder.builder();
			if (uuidSource != null){
				builder.uuid(new UUID(0, uuidSource));
			}
			if (messageTypeSource != null){
				builder.type(MessageType.valueOf(messageTypeSource));
			}
			if (contentType != null) {
				builder.contentType(contentType);
			}
			if (content != null){
				builder.content(content);
			}
			if (fromSource != null){
				builder.from(new TestAddress(fromSource, 8080));
			}
			if (toSource != null){
				builder.to(new TestAddress(toSource, 8080));
			}
			builder.build();
		});

		assertThat(throwable)
			.isInstanceOf(PayloadMessageBuilderException.class)
			.hasMessage(expectedMessage);
	}

	@Test
	void shouldCheckFailBuilding_ifTypeIsInvalid(){
		String expectedMessage = "Absent fields: uuid & contentType & content & from & to; " +
			"type is invalid it must be either REQUEST or RESPONSE";
		Throwable throwable = catchThrowable(() -> {
			PayloadMessageBuilder.builder()
				.type(MessageType.CONFIRMATION)
				.build();
		});
		assertThat(throwable)
			.isInstanceOf(PayloadMessageBuilderException.class)
			.hasMessage(expectedMessage);
	}

	@Test
	void shouldCheckFailBuilding_ifContentTypeIsInvalid(){
		String expectedMessage = "Absent fields: uuid & type & content & from & to; " +
			"contentType must not be empty";
		Throwable throwable = catchThrowable(() -> {
			PayloadMessageBuilder.builder()
				.contentType("")
				.build();
		});
		assertThat(throwable)
			.isInstanceOf(PayloadMessageBuilderException.class)
			.hasMessage(expectedMessage);
	}

	@Test
	void shouldCheckFailBuilding_ifContentIsInvalid(){
		String expectedMessage = "Absent fields: uuid & type & contentType & from & to; " +
			"content must not be empty";
		Throwable throwable = catchThrowable(() -> {
			PayloadMessageBuilder.builder()
				.content("")
				.build();
		});
		assertThat(throwable)
			.isInstanceOf(PayloadMessageBuilderException.class)
			.hasMessage(expectedMessage);
	}

	@Test
	void shouldCheckBuilding() {
		PayloadMessage message = PayloadMessageBuilder.builder()
			.uuid(EXPECTED_UUID)
			.type(EXPECTED_TYPE)
			.contentType(EXPECTED_CONTENT_TYPE)
			.content(EXPECTED_CONTENT)
			.from(EXPECTED_FROM)
			.to(EXPECTED_TO)
			.build();

		assertThat(message.getUuid()).isEqualTo(EXPECTED_UUID);
		assertThat(message.getType()).isEqualTo(EXPECTED_TYPE);
		assertThat(message.getContentType()).isEqualTo(EXPECTED_CONTENT_TYPE);
		assertThat(message.getContent()).isEqualTo(EXPECTED_CONTENT);
		assertThat(message.getFrom()).isEqualTo(EXPECTED_FROM);
		assertThat(message.getTo()).isEqualTo(EXPECTED_TO);
	}

	@SneakyThrows
	@Test
	void shouldCheckReset() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder()
			.uuid(EXPECTED_UUID)
			.type(EXPECTED_TYPE)
			.contentType(EXPECTED_CONTENT_TYPE)
			.content(EXPECTED_CONTENT)
			.from(EXPECTED_FROM)
			.to(EXPECTED_TO)
			.reset();

		assertThat(Reflections.get(builder, "uuid", UUID.class)).isNull();
		assertThat(Reflections.get(builder, "type", MessageType.class)).isNull();
		assertThat(Reflections.get(builder, "contentType", String.class)).isNull();
		assertThat(Reflections.get(builder, "content", String.class)).isNull();
		assertThat(Reflections.get(builder, "from", Address.class)).isNull();
		assertThat(Reflections.get(builder, "to", Address.class)).isNull();
	}
}
