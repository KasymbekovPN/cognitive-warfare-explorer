package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersProperties;
import ru.cwe.common.test.fakers.FakersProperty;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleIntegerFakersStrategyTest {

	@Test
	void shouldCheckExecution_ifBadProperties() {
		SimpleIntegerFakersStrategy strategy = new SimpleIntegerFakersStrategy();
		Object result = strategy.execute(new Faker(), new FakersProperties());

		assertThat(result).isNull();
	}

	@Test
	void shouldCheckExecution() {
		FakersProperties properties = new FakersProperties().put(FakersProperty.OPERATION, FakersProperty.SIMPLE);

		SimpleIntegerFakersStrategy strategy = new SimpleIntegerFakersStrategy();
		Object result = strategy.execute(new Faker(), properties);

		assertThat(result).isInstanceOf(Integer.class);
	}
}
