package ru.cwe.common.test.fakers.strategy.string;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersPropertiesOld;
import ru.cwe.common.test.fakers.exception.BadFakersStrategyPropertiesException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class StringFakersStrategyOldOldTestOld {

	@Test
	void shouldCheckBadExecutionScenario() {
		FakersPropertiesOld properties = new FakersPropertiesOld();
		Throwable throwable = catchThrowable(() -> {
			Object result = new StringFakersStrategyOldOld().execute(new Faker(), properties);
		});
		assertThat(throwable).isInstanceOf(BadFakersStrategyPropertiesException.class);
	}
}
