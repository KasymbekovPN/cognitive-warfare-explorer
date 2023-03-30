package ru.cwe.conversation.message;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.assertj.core.api.Assertions.assertThat;

class VersionTest {

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckCheckingMethod.csv")
	void shouldCheckCheckingMethod(int version, boolean expected) {
		assertThat(Version.check(version)).isEqualTo(expected);
	}
}
