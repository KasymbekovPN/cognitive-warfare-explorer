package ru.cwe.common.test.fakers;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ByteFakerTest {

	@Test
	void shouldCheckRandomMethod() {
		byte value = new ByteFaker(new Faker()).random();

		assertThat(value)
			.isGreaterThanOrEqualTo(Byte.MIN_VALUE)
			.isLessThanOrEqualTo(Byte.MAX_VALUE);
	}

	@Test
	void shouldCheckArrayMethod() {
		int size = 10;
		byte[] result = new ByteFaker(new Faker()).array(size);

		System.out.println(Arrays.toString(result));
		assertThat(result).hasSize(size);
	}
}
