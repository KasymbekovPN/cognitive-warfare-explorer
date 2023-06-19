package ru.cwe.common.test.fakers.strategy;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringFakerTest {

	@Test
	void shouldCheckRandomMethod() {
		String value = new StringFaker(new Faker()).random();
		int length = value.length();
		assertThat(length)
			.isGreaterThanOrEqualTo(StringFaker.MIN_LEN)
			.isLessThanOrEqualTo(StringFaker.MAX_LEN);

		for (int i = 0; i < length; i++) {
			char ch = value.charAt(i);
			assertThat((int) ch)
				.isGreaterThanOrEqualTo(StringFaker.MIN_CHAR_CODE)
				.isLessThanOrEqualTo(StringFaker.MAX_CHAR_CODE);
		}
	}
}
