package ru.cwe.common.test.fakers.strategy.string;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersProperties;
import ru.cwe.common.test.fakers.FakersProperty;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleStringFakersStrategyTest {

	@Test
	void shouldCheckExecution_ifBadProperties() {
		Object result = new SimpleStringFakersStrategy().execute(new Faker(), new FakersProperties());
		assertThat(result).isNull();
	}

	@Test
	void shouldCheckExecution() {
		FakersProperties properties = new FakersProperties()
			.put(FakersProperty.OPERATION, FakersProperty.SIMPLE);
		Object result = new SimpleStringFakersStrategy().execute(new Faker(), properties);
		assertThat(result).isInstanceOf(String.class);

		String castResult = (String) result;
		assertThat(castResult.substring(0, 3)).isEqualTo(SimpleStringFakersStrategy.PREFIX);
	}
}
