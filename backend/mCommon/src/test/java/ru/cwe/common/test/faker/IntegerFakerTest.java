package ru.cwe.common.test.faker;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class IntegerFakerTest {

	@RepeatedTest(1_000)
	void shouldCheckValueGetting() {
		int value = new IntegerFaker(new Faker()).value();
		assertThat(value).isGreaterThanOrEqualTo(Integer.MIN_VALUE);
		assertThat(value).isLessThanOrEqualTo(Integer.MAX_VALUE);
	}

	@RepeatedTest(1_000)
	void shouldCheckBetweenValueGetting() {
		int min = -1_000_000;
		int max = 1_000_000;
		int value = new IntegerFaker(new Faker()).between(min, max);

		assertThat(value).isGreaterThanOrEqualTo(min);
		assertThat(value).isLessThan(max);
	}

	@RepeatedTest(1_000)
	void shouldCheckLessThanValueGetting() {
		int threshold = 1_000;
		int value = new IntegerFaker(new Faker()).lessThan(threshold);

		assertThat(value).isGreaterThanOrEqualTo(Integer.MIN_VALUE);
		assertThat(value).isLessThan(threshold);
	}

	@RepeatedTest(1_000)
	void shouldCheckMoreThenValueGetting() {
		int threshold = 1_000;
		int value = new IntegerFaker(new Faker()).moreThan(threshold);

		assertThat(value).isGreaterThan(threshold);
		assertThat(value).isLessThanOrEqualTo(Integer.MAX_VALUE);
	}
}
