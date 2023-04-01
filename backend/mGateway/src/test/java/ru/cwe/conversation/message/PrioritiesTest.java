package ru.cwe.conversation.message;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.assertj.core.api.Assertions.assertThat;

class PrioritiesTest {

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckPriorityChecking.csv")
	void shouldCheckPriorityChecking(int priority, int expected) {
		assertThat(Priorities.check(priority)).isEqualTo(expected);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckPriorityAdjustment.csv")
	void shouldCheckPriorityAdjustment(int rawPriority, int expectedPriority) {
		assertThat(Priorities.adjust(rawPriority)).isEqualTo(expectedPriority);
	}
}
