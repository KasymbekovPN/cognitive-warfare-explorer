package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersProperties;
import ru.cwe.common.test.fakers.FakersProperty;

import static org.assertj.core.api.Assertions.assertThat;

class BetweenIntegerFakersStrategyTest {

	@Test
	void shouldCheckExecution_ifBadProperties() {
		Object result = new BetweenIntegerFakersStrategy().execute(new Faker(), new FakersProperties());
		assertThat(result).isNull();
	}

	@RepeatedTest(1_000)
	void shouldCheckExecution() {
		int min = -10;
		int max = 10;
		FakersProperties properties = new FakersProperties()
			.put(FakersProperty.OPERATION, FakersProperty.BETWEEN)
			.put(FakersProperty.MIN, min)
			.put(FakersProperty.MAX, max);

		Object result = new BetweenIntegerFakersStrategy().execute(new Faker(), properties);
		assertThat(result).isInstanceOf(Integer.class);

		Integer castResult = (Integer) result;
		assertThat(min).isLessThanOrEqualTo(castResult);
		assertThat(max).isGreaterThan(castResult);
	}
}
