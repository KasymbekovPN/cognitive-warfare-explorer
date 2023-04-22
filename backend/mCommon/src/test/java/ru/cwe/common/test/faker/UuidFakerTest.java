package ru.cwe.common.test.faker;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UuidFakerTest {

	@RepeatedTest(1_000)
	void shouldCheckUuid() {
		UUID uuid = new UuidFaker(new Faker()).uuid();

		assertThat(uuid).isNotNull();
	}
}
