package ru.cwe.conversation.message.payload;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.common.reflection.Reflections;
import utils.TestAddress;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class PayloadMessageBuilderTest {
	private int expectedVersion;
	private int expectedPriority;
	private UUID expectedUuid;
	private MessageType expectedType;
	private String expectedContentType;
	private String expectedContent;
	private Address expectedFrom;
	private Address expectedTo;

	@BeforeEach
	void setUp() {
		expectedVersion = Fakers.message().version();
		expectedPriority = Fakers.message().priority();
		expectedUuid = Fakers.base().uuid().uuid();
		expectedType = MessageType.RESPONSE;
		expectedContentType = Fakers.base().string().string();
		expectedContent = Fakers.base().string().string();
		expectedFrom = Fakers.address().address();
		expectedTo = Fakers.address().address();
	}

	@SneakyThrows
	@Test
	void shouldCheckVersion() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder().version(expectedVersion);

		assertThat(Reflections.get(builder, "version", Integer.class)).isEqualTo(expectedVersion);
	}

	@SneakyThrows
	@Test
	void shouldCheckPriority() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder().priority(expectedPriority);

		assertThat(Reflections.get(builder, "priority", Integer.class)).isEqualTo(expectedPriority);
	}

	@SneakyThrows
	@Test
	void shouldCheckUuid() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder().uuid(expectedUuid);

		assertThat(Reflections.get(builder, "uuid", UUID.class)).isEqualTo(expectedUuid);
	}

	@SneakyThrows
	@Test
	void shouldCheckType() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder().type(expectedType);

		assertThat(Reflections.get(builder, "type", MessageType.class)).isEqualTo(expectedType);
	}

	@SneakyThrows
	@Test
	void shouldCheckContentType() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder().contentType(expectedContentType);

		assertThat(Reflections.get(builder, "contentType", String.class)).isEqualTo(expectedContentType);
	}

	@SneakyThrows
	@Test
	void shouldCheckContent() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder().content(expectedContent);

		assertThat(Reflections.get(builder, "content", String.class)).isEqualTo(expectedContent);
	}

	@SneakyThrows
	@Test
	void shouldCheckFrom() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder().from(expectedFrom);

		assertThat(Reflections.get(builder, "from", Address.class)).isEqualTo(expectedFrom);
	}

	@SneakyThrows
	@Test
	void shouldCheckTo() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder().to(expectedTo);

		assertThat(Reflections.get(builder, "to", Address.class)).isEqualTo(expectedTo);
	}

	@SneakyThrows
	@Test
	void shouldCheckRequest() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder().request();

		assertThat(Reflections.get(builder, "uuid")).isInstanceOf(UUID.class);
		assertThat(Reflections.get(builder, "type", MessageType.class)).isEqualTo(MessageType.REQUEST);
	}

	@SneakyThrows
	@Test
	void shouldCheckResponse() {
		TestPayloadMessage request = new TestPayloadMessage(
			expectedVersion,
			expectedPriority,
			MessageType.REQUEST,
			expectedUuid,
			expectedContentType,
			expectedContent,
			expectedFrom,
			expectedTo
		);
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder()
			.response(request);

		assertThat(Reflections.get(builder, "version", Integer.class)).isEqualTo(expectedVersion);
		assertThat(Reflections.get(builder, "priority", Integer.class)).isEqualTo(expectedPriority);
		assertThat(Reflections.get(builder, "uuid", UUID.class)).isEqualTo(expectedUuid);
		assertThat(Reflections.get(builder, "type", MessageType.class)).isEqualTo(MessageType.RESPONSE);
		assertThat(Reflections.get(builder, "from", Address.class)).isEqualTo(expectedTo);
		assertThat(Reflections.get(builder, "to", Address.class)).isEqualTo(expectedFrom);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckFailBuilding.csv")
	void shouldCheckFailBuilding_ifSomeIsNull(Integer version,
											  Integer uuidSource,
											  String messageTypeSource,
											  String contentType,
											  String content,
											  String fromSource,
											  String toSource,
											  String expectedMessage) {
		Throwable throwable = catchThrowable(() -> {
			PayloadMessageBuilder builder = PayloadMessageBuilder.builder();
			if (version != null){
				builder.version(version);
			}
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
				builder.from(new TestAddress(fromSource, Fakers.address().port()));
			}
			if (toSource != null){
				builder.to(new TestAddress(toSource, Fakers.address().port()));
			}
			builder.build();
		});

		assertThat(throwable)
			.isInstanceOf(PayloadMessageBuilderException.class)
			.hasMessage(expectedMessage);
	}

	@Test
	void shouldCheckFailBuilding_ifTypeIsInvalid(){
		String expectedMessage = "Absent fields: version & uuid & contentType & content & from & to; " +
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
		String expectedMessage = "Absent fields: version & uuid & type & content & from & to; " +
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
		String expectedMessage = "Absent fields: version & uuid & type & contentType & from & to; " +
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
			.version(expectedVersion)
			.priority(expectedPriority)
			.uuid(expectedUuid)
			.type(expectedType)
			.contentType(expectedContentType)
			.content(expectedContent)
			.from(expectedFrom)
			.to(expectedTo)
			.build();

		assertThat(message.getVersion()).isEqualTo(expectedVersion);
		assertThat(message.getPriority()).isEqualTo(expectedPriority);
		assertThat(message.getUuid()).isEqualTo(expectedUuid);
		assertThat(message.getType()).isEqualTo(expectedType);
		assertThat(message.getContentType()).isEqualTo(expectedContentType);
		assertThat(message.getContent()).isEqualTo(expectedContent);
		assertThat(message.getFrom()).isEqualTo(expectedFrom);
		assertThat(message.getTo()).isEqualTo(expectedTo);
	}

	@SneakyThrows
	@Test
	void shouldCheckReset() {
		PayloadMessageBuilder builder = PayloadMessageBuilder.builder()
			.version(expectedVersion)
			.priority(expectedPriority)
			.uuid(expectedUuid)
			.type(expectedType)
			.contentType(expectedContentType)
			.content(expectedContent)
			.from(expectedFrom)
			.to(expectedTo)
			.reset();

		assertThat(Reflections.get(builder, "version", Integer.class)).isNull();
		assertThat(Reflections.get(builder, "priority", Integer.class)).isZero();
		assertThat(Reflections.get(builder, "uuid", UUID.class)).isNull();
		assertThat(Reflections.get(builder, "type", MessageType.class)).isNull();
		assertThat(Reflections.get(builder, "contentType", String.class)).isNull();
		assertThat(Reflections.get(builder, "content", String.class)).isNull();
		assertThat(Reflections.get(builder, "from", Address.class)).isNull();
		assertThat(Reflections.get(builder, "to", Address.class)).isNull();
	}
}
