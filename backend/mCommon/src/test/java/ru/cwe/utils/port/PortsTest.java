package ru.cwe.utils.port;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class PortsTest {

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckInRangeMethod.csv")
	void shouldCheckInRangeMethod(int rawValue, int compareResult) {
		Assertions.assertThat(Ports.checkInRange(rawValue)).isEqualTo(compareResult);
	}
}
