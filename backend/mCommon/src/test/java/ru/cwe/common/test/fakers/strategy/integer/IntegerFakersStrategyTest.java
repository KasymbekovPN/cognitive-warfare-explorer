package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersProperty;
import ru.cwe.common.test.fakers.exception.BadFakersStrategyPropertiesException;

import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class IntegerFakersStrategyTest {

	@Test
	void shouldCheckBadExecutionScenario() {
		EnumMap<FakersProperty, Object> properties = new EnumMap<>(FakersProperty.class);
		Throwable throwable = catchThrowable(() -> {
			Object result = new IntegerFakersStrategy().execute(new Faker(), properties);
		});
		assertThat(throwable).isInstanceOf(BadFakersStrategyPropertiesException.class);
	}
}
