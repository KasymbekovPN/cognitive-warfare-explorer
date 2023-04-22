package ru.cwe.common.test.faker;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.cwe.common.reflection.Reflections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BaseFakerTest {

	@SneakyThrows
	@Test
	void shouldCheckIntegerFakerGetting() {
		IntegerFaker integerFaker = new BaseFaker(new Faker()).integer();

		assertThat(integerFaker).isNotNull();
		assertThat(Reflections.get(integerFaker, "core")).isInstanceOf(Faker.class);
	}

	@SneakyThrows
	@Test
	void shouldCheckStringFakerGetting() {
		StringFaker stringFaker = new BaseFaker(new Faker()).string();

		assertThat(stringFaker).isNotNull();
		assertThat(Reflections.get(stringFaker, "core")).isInstanceOf(Faker.class);
	}

	@SneakyThrows
	@Test
	void shouldCheckUuidFakerGetting() {
		UuidFaker uuidFaker = new BaseFaker(new Faker()).uuid();

		assertThat(uuidFaker).isNotNull();
		assertThat(Reflections.get(uuidFaker, "core")).isInstanceOf(Faker.class);
	}
}
