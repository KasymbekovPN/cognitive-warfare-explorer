package ru.cwe.common.test.fakers;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class DoubleFakerTest {

	@Test
	void shouldCheckRandomMethod() {
		double value = new DoubleFaker(new Faker()).random();

		assertThat(value)
			.isGreaterThanOrEqualTo(Double.MIN_VALUE)
			.isLessThanOrEqualTo(Double.MAX_VALUE);
	}

	@Test
	void shouldCheckListMethod() {
		int size = 10;
		List<Double> result = new DoubleFaker(new Faker()).list(size);

		assertThat(result.size()).isEqualTo(size);
		for (Double value : result) {
			assertThat(value)
				.isGreaterThanOrEqualTo(DoubleFaker.MIN)
				.isLessThanOrEqualTo(DoubleFaker.MAX);
		}
	}
}
