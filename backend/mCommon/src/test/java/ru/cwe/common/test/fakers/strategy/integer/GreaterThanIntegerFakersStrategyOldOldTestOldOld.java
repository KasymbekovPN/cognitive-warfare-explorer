package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersPropertiesOld;
import ru.cwe.common.test.fakers.FakersPropertyOld;

import static org.assertj.core.api.Assertions.assertThat;

class GreaterThanIntegerFakersStrategyOldOldTestOldOld {
	@Test
	void shouldCheckExecution_ifBadProperties() {
		Object result = new GreaterThanIntegerFakersStrategyOldOld().execute(new Faker(), new FakersPropertiesOld());
		assertThat(result).isNull();
	}

	@Test
	void shouldCheckExecution() {
		int threshold = 0;
		FakersPropertiesOld properties = new FakersPropertiesOld()
			.put(FakersPropertyOld.OPERATION, FakersPropertyOld.GREATER)
			.put(FakersPropertyOld.THRESHOLD, threshold);

		Object result = new GreaterThanIntegerFakersStrategyOldOld().execute(new Faker(), properties);

		assertThat(result).isInstanceOf(Integer.class);

		Integer castResult = (Integer) result;
		assertThat(castResult).isGreaterThan(threshold);
	}
}
