package ru.cwe.common.test.fakers.strategy.string;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersProperties;
import ru.cwe.common.test.fakers.exception.BadFakersStrategyPropertiesException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class StringFakersStrategyTest {

	@Test
	void shouldCheckBadExecutionScenario() {
		FakersProperties properties = new FakersProperties();
		Throwable throwable = catchThrowable(() -> {
			Object result = new StringFakersStrategy().execute(new Faker(), properties);
		});
		assertThat(throwable).isInstanceOf(BadFakersStrategyPropertiesException.class);
	}
}
