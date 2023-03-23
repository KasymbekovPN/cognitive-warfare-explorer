// TODO: 23.03.2023 ??
//package ru.cwe.conversation.type;
//
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.catchThrowable;
//
//class ContentTypeBuilderTest {
//
//	@Test
//	void shouldCheckBuilding_ifTypeNull() {
//		Throwable throwable = catchThrowable(() -> {
//			new MessageTypeBuilder().build();
//		});
//		assertThat(throwable)
//			.isInstanceOf(MessageTypeBuildingRuntimeException.class)
//			.hasMessage("Type is null");
//	}
//
//	@Test
//	void shouldCheckBuilding_ifTypeIsBlank() {
//		Throwable throwable = catchThrowable(() -> {
//			new MessageTypeBuilder()
//				.type("")
//				.build();
//		});
//		assertThat(throwable)
//			.isInstanceOf(MessageTypeBuildingRuntimeException.class)
//			.hasMessage("Type is wrong");
//	}
//
//	@Test
//	void shouldCheckBuilding() {
//		String expected = "some.type";
//		ContentType contentType = new MessageTypeBuilder()
//			.type(expected)
//			.build();
//
//		assertThat(contentType.getName()).isEqualTo(expected);
//	}
//}
