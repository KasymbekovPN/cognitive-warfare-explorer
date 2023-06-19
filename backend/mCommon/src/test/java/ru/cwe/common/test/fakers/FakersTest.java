package ru.cwe.common.test.fakers;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FakersTest {

	@Test
	void shouldCheckIntegerMethod() {
		IntegerFaker faker = Fakers.int_();

		assertThat(faker).isNotNull();
	}

	@Test
	void shouldCheckStringMethod() {
		StringFaker faker = Fakers.str_();

		assertThat(faker).isNotNull();
	}

	@Test
	void shouldCheckUuidMethod() {
		UuidFaker faker = Fakers.uuid_();

		assertThat(faker).isNotNull();
	}
}
