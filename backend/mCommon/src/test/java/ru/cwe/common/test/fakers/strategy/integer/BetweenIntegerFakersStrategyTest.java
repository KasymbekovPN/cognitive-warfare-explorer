package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersProperty;

import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;

class BetweenIntegerFakersStrategyTest {

	@Test
	void shouldCheckExecution_ifBadProperties() {
		EnumMap<FakersProperty, Object> properties = new EnumMap<>(FakersProperty.class);
		Object result = new BetweenIntegerFakersStrategy().execute(new Faker(), properties);

		assertThat(result).isNull();
	}

	@RepeatedTest(1_000)
	void shouldCheckExecution() {
		int min = -10;
		int max = 10;
		EnumMap<FakersProperty, Object> properties = new EnumMap<>(FakersProperty.class);
		properties.put(FakersProperty.OPERATION, FakersProperty.BETWEEN);
		properties.put(FakersProperty.MIN, min);
		properties.put(FakersProperty.MAX, max);

		Object result = new BetweenIntegerFakersStrategy().execute(new Faker(), properties);
		assertThat(result).isInstanceOf(Integer.class);

		Integer castResult = (Integer) result;
		assertThat(min).isLessThanOrEqualTo(castResult);
		assertThat(max).isGreaterThan(castResult);
	}
}
