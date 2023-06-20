package ru.cwe.common.test.fakers;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LongFakerTest {

	@Test
	void shouldCheckRandomMethod() {
		long value = new LongFaker(new Faker()).random();

		assertThat(value)
			.isGreaterThanOrEqualTo(Long.MIN_VALUE)
			.isLessThanOrEqualTo(Long.MAX_VALUE);
	}

	@Test
	void shouldCheckBetweenMethod() {
		long min = -10L;
		long max = 10L;
		long value = new LongFaker(new Faker()).between(min, max);

		assertThat(value)
			.isGreaterThanOrEqualTo(min)
			.isLessThan(max);
	}

	@Test
	void shouldCheckGreaterMethod() {
		long threshold = 10L;
		long value = new LongFaker(new Faker()).greater(threshold);

		assertThat(value)
			.isGreaterThan(threshold)
			.isLessThan(Long.MAX_VALUE);
	}

	@Test
	void shouldCheckLessMethod() {
		long threshold = 10L;
		long value = new LongFaker(new Faker()).less(threshold);

		assertThat(value)
			.isGreaterThanOrEqualTo(Long.MIN_VALUE)
			.isLessThan(threshold);
	}

}
