package ru.cwe.common.delimiter;

import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.Fakers;

import static org.assertj.core.api.Assertions.assertThat;

class FirstDelimiterTest {

	@Test
	void shouldCheckNextMethod() {
		int size = Fakers.int_().between(5, 15);
		String first = Fakers.str_().random();
		String next = Fakers.str_().random();

		FirstDelimiter delimiter = new FirstDelimiter(first, next);
		assertThat(delimiter.next()).isEqualTo(first);
		for (int i = 0; i < size; i++) {
			assertThat(delimiter.next()).isEqualTo(next);
		}
	}
}
