// TODO: 23.03.2023 ???
//package ru.cwe.conversation.message;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//import ru.cwe.conversation.address.Address;
//import ru.cwe.conversation.type.ContentType;
//import utils.TestAddress;
//import utils.TestContentType;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.catchThrowable;
//
//class MessageBuilderTest {
//
//	private static final ContentType EXPECTED_TYPE = new TestContentType("some.message.type");
//	private static final String EXPECTED_CONTENT = "some.content";
//	private static final Address EXPECTED_FROM = new TestAddress("some.from", 0);
//	private static final Address EXPECTED_TO = new TestAddress("some.to", 0);
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "shouldCheckFailMessageBuilding.csv")
//	void shouldCheckFailMessageBuilding(String type, String content, String from, String to, String expectedMessage) {
//		MessageBuilder builder = new MessageBuilder();
//		if (type != null){
//			builder.type(new TestContentType(type));
//		}
//		if (content != null){
//			builder.content(content);
//		}
//		if (from != null){
//			builder.from(new TestAddress(from, 0));
//		}
//		if (to != null){
//			builder.to(new TestAddress(to, 0));
//		}
//		Throwable throwable = catchThrowable(() -> {
//			MessageOLd build = builder.build();
//		});
//
//		assertThat(throwable)
//			.isInstanceOf(MessageBuildingRuntimeException.class)
//			.hasMessage(expectedMessage);
//	}
//
//	@Test
//	void shouldCheckMessageBuilding_ifItRequest() {
//		MessageOLd message = new MessageBuilder()
//			.type(EXPECTED_TYPE)
//			.content(EXPECTED_CONTENT)
//			.from(EXPECTED_FROM)
//			.to(EXPECTED_TO)
//			.build();
//		assertThat(message.isResponse()).isFalse();
//		assertThat(message.getType()).isEqualTo(EXPECTED_TYPE);
//		assertThat(message.getContent()).isEqualTo(EXPECTED_CONTENT);
//		assertThat(message.getFrom()).isEqualTo(EXPECTED_FROM);
//		assertThat(message.getTo()).isEqualTo(EXPECTED_TO);
//	}
//
//	@Test
//	void shouldCheckResponseBuilding() {
//		MessageOLd request = new MessageBuilder()
//			.type(EXPECTED_TYPE)
//			.content(EXPECTED_CONTENT)
//			.from(EXPECTED_FROM)
//			.to(EXPECTED_TO)
//			.build();
//
//		MessageOLd message = new MessageBuilder(request)
//			.type(EXPECTED_TYPE)
//			.content(EXPECTED_CONTENT)
//			.build();
//		assertThat(message.isResponse()).isTrue();
//		assertThat(message.getType()).isEqualTo(EXPECTED_TYPE);
//		assertThat(message.getContent()).isEqualTo(EXPECTED_CONTENT);
//		assertThat(message.getFrom()).isEqualTo(EXPECTED_TO);
//		assertThat(message.getTo()).isEqualTo(EXPECTED_FROM);
//	}
//}
