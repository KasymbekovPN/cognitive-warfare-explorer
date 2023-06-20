package ru.cwe.common.test.fakers;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FakersTest {

	@Test
	void shouldCheckIntMethod() {
		IntegerFaker faker = Fakers.int_();

		assertThat(faker).isNotNull();
	}

	@Test
	void shouldCheckStrMethod() {
		StringFaker faker = Fakers.str_();

		assertThat(faker).isNotNull();
	}

	@Test
	void shouldCheckUuidMethod() {
		UuidFaker faker = Fakers.uuid_();

		assertThat(faker).isNotNull();
	}

	@Test
	void shouldCheckLongMethod() {
		LongFaker faker = Fakers.long_();

		assertThat(faker).isNotNull();
	}
}
