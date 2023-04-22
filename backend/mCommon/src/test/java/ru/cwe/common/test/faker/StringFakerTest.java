package ru.cwe.common.test.faker;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringFakerTest {

	@Test
	void shouldCheckString() {

		String string = new StringFaker(new Faker()).string();

		assertThat(string).isNotNull();
		String prefix = "randomString";
		assertThat(string.substring(0, prefix.length())).isEqualTo(prefix);
	}
}
