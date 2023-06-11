package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersProperty;

import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;

class GreaterThanIntegerFakersStrategyTest {
	@Test
	void shouldCheckExecution_ifBadProperties() {
		EnumMap<FakersProperty, Object> properties = new EnumMap<>(FakersProperty.class);
		Object result = new GreaterThanIntegerFakersStrategy().execute(new Faker(), properties);

		assertThat(result).isNull();
	}

	@Test
	void shouldCheckExecution() {
		int threshold = 0;
		EnumMap<FakersProperty, Object> properties = new EnumMap<>(FakersProperty.class);
		properties.put(FakersProperty.OPERATION, FakersProperty.GREATER);
		properties.put(FakersProperty.THRESHOLD, threshold);

		Object result = new GreaterThanIntegerFakersStrategy().execute(new Faker(), properties);

		assertThat(result).isInstanceOf(Integer.class);

		Integer castResult = (Integer) result;
		assertThat(castResult).isGreaterThan(threshold);
	}
}
