package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersProperty;

import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleIntegerFakersStrategyTest {

	@Test
	void shouldCheckExecution_ifBadProperties() {
		SimpleIntegerFakersStrategy strategy = new SimpleIntegerFakersStrategy();
		Object result = strategy.execute(new Faker(), new EnumMap<>(FakersProperty.class));

		assertThat(result).isNull();
	}

	@Test
	void shouldCheckExecution() {
		EnumMap<FakersProperty, Object> properties = new EnumMap<>(FakersProperty.class);
		properties.put(FakersProperty.OPERATION, FakersProperty.SIMPLE);

		SimpleIntegerFakersStrategy strategy = new SimpleIntegerFakersStrategy();
		Object result = strategy.execute(new Faker(), properties);

		assertThat(result).isInstanceOf(Integer.class);
	}
}
