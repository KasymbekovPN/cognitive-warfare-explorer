package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersPropertiesOld;
import ru.cwe.common.test.fakers.FakersPropertyOld;

import static org.assertj.core.api.Assertions.assertThat;

class BetweenIntegerFakersStrategyOldOldTestOldOld {

	@Test
	void shouldCheckExecution_ifBadProperties() {
		Object result = new BetweenIntegerFakersStrategyOldOld().execute(new Faker(), new FakersPropertiesOld());
		assertThat(result).isNull();
	}

	@RepeatedTest(1_000)
	void shouldCheckExecution() {
		int min = -10;
		int max = 10;
		FakersPropertiesOld properties = new FakersPropertiesOld()
			.put(FakersPropertyOld.OPERATION, FakersPropertyOld.BETWEEN)
			.put(FakersPropertyOld.MIN, min)
			.put(FakersPropertyOld.MAX, max);

		Object result = new BetweenIntegerFakersStrategyOldOld().execute(new Faker(), properties);
		assertThat(result).isInstanceOf(Integer.class);

		Integer castResult = (Integer) result;
		assertThat(min).isLessThanOrEqualTo(castResult);
		assertThat(max).isGreaterThan(castResult);
	}
}
