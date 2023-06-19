package ru.cwe.common.test.fakers.strategy;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UuidFakerTest {

	@Test
	void shouldCheckRandomMethod() {
		UUID value = new UuidFaker(new Faker()).random();

		assertThat(value).isNotNull();
	}
}
