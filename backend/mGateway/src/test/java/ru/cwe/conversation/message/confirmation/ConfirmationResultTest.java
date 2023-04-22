// TODO: 22.04.2023 del + csv
//package ru.cwe.conversation.message.confirmation;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class ConfirmationResultTest {
//
//	@Test
//	void shouldCheckInvalidConfirmationResultGetting() {
//		assertThat(ConfirmationResult.INVALID.getValue()).isEqualTo(0);
//	}
//
//	@Test
//	void shouldCheckRequestConfirmationResultGetting() {
//		assertThat(ConfirmationResult.REQUEST.getValue()).isEqualTo(1);
//	}
//
//	@Test
//	void shouldCheckResponseConfirmationResultGetting() {
//		assertThat(ConfirmationResult.RESPONSE.getValue()).isEqualTo(2);
//	}
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "shouldCheckValueOfCreationFromInt.csv")
//	void shouldCheckValueOfCreationFromInt(Integer asInt, String asString) {
//		assertThat(ConfirmationResult.valueOf(asInt))
//			.isEqualTo(ConfirmationResult.valueOf(asString));
//	}
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "shouldCheckChecking.csv")
//	void shouldCheckChecking(int source, boolean expected) {
//		assertThat(ConfirmationResult.check(source)).isEqualTo(expected);
//	}
//}
