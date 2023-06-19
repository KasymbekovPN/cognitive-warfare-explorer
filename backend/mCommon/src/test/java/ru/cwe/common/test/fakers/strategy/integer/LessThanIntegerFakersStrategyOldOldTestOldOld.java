package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersPropertiesOld;
import ru.cwe.common.test.fakers.FakersPropertyOld;

import static org.assertj.core.api.Assertions.assertThat;

class LessThanIntegerFakersStrategyOldOldTestOldOld {

	@Test
	void shouldCheckExecution_ifBadProperties() {
		Object result = new LessThanIntegerFakersStrategyOldOld().execute(new Faker(), new FakersPropertiesOld());
		assertThat(result).isNull();
	}

	@Test
	void shouldCheckExecution() {
		int threshold = 0;
		FakersPropertiesOld properties = new FakersPropertiesOld()
			.put(FakersPropertyOld.OPERATION, FakersPropertyOld.LESS)
			.put(FakersPropertyOld.THRESHOLD, threshold);

		Object result = new LessThanIntegerFakersStrategyOldOld().execute(new Faker(), properties);

		assertThat(result).isInstanceOf(Integer.class);

		Integer castResult = (Integer) result;
		assertThat(castResult).isLessThan(threshold);
	}
}
