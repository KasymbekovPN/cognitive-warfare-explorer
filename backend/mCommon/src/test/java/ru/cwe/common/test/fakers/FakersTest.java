package ru.cwe.common.test.fakers;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FakersTest {

	@Test
	void shouldCheckIntegerMethod() {
		IntegerFaker faker = Fakers.integer();

		assertThat(faker).isNotNull();
	}

	@Test
	void shouldCheckStringMethod() {
		StringFaker faker = Fakers.str();

		assertThat(faker).isNotNull();
	}

	@Test
	void shouldCheckUuidMethod() {
		UuidFaker faker = Fakers.uuid();

		assertThat(faker).isNotNull();
	}
}
