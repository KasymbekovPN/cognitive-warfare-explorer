package ru.cwe.common.test.fakers.strategy;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IntegerFakerTest {

	@Test
	void shouldCheckRandomMethod() {
		int value = new IntegerFaker(new Faker()).random();

		assertThat(value)
			.isGreaterThanOrEqualTo(Integer.MIN_VALUE)
			.isLessThanOrEqualTo(Integer.MAX_VALUE);
	}

	@Test
	void shouldCheckBetweenMethod() {
		int min = -10;
		int max = 10;
		int value = new IntegerFaker(new Faker()).between(min, max);

		assertThat(value)
			.isGreaterThanOrEqualTo(min)
			.isLessThan(max);
	}

	@Test
	void shouldCheckGreaterMethod() {
		int threshold = 10;
		int value = new IntegerFaker(new Faker()).greater(threshold);

		assertThat(value)
			.isGreaterThan(threshold)
			.isLessThan(Integer.MAX_VALUE);
	}

	@Test
	void shouldCheckLessMethod() {
		int threshold = 10;
		int value = new IntegerFaker(new Faker()).less(threshold);

		assertThat(value)
			.isGreaterThanOrEqualTo(Integer.MIN_VALUE)
			.isLessThan(threshold);
	}
}
