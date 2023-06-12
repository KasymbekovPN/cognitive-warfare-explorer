package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersProperties;
import ru.cwe.common.test.fakers.FakersProperty;

import static org.assertj.core.api.Assertions.assertThat;

class LessThanIntegerFakersStrategyTest {

	@Test
	void shouldCheckExecution_ifBadProperties() {
		Object result = new LessThanIntegerFakersStrategy().execute(new Faker(), new FakersProperties());
		assertThat(result).isNull();
	}

	@Test
	void shouldCheckExecution() {
		int threshold = 0;
		FakersProperties properties = new FakersProperties()
			.put(FakersProperty.OPERATION, FakersProperty.LESS)
			.put(FakersProperty.THRESHOLD, threshold);

		Object result = new LessThanIntegerFakersStrategy().execute(new Faker(), properties);

		assertThat(result).isInstanceOf(Integer.class);

		Integer castResult = (Integer) result;
		assertThat(castResult).isLessThan(threshold);
	}
}
