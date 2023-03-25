package ru.cwe.utils.delimiter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FirstDelimiterTest {

	@Test
	void shouldCheckNextMethod() {
		int size = 10;
		String first = "first";
		String next = "next";

		FirstDelimiter delimiter = new FirstDelimiter(first, next);
		assertThat(delimiter.next()).isEqualTo(first);
		for (int i = 0; i < size; i++) {
			assertThat(delimiter.next()).isEqualTo(next);
		}
	}
}
