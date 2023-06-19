package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersPropertiesOld;
import ru.cwe.common.test.fakers.FakersPropertyOld;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleIntegerFakersStrategyOldTestOldOld {

	@Test
	void shouldCheckExecution_ifBadProperties() {
		SimpleIntegerFakersStrategyOldOld strategy = new SimpleIntegerFakersStrategyOldOld();
		Object result = strategy.execute(new Faker(), new FakersPropertiesOld());

		assertThat(result).isNull();
	}

	@Test
	void shouldCheckExecution() {
		FakersPropertiesOld properties = new FakersPropertiesOld().put(FakersPropertyOld.OPERATION, FakersPropertyOld.SIMPLE);

		SimpleIntegerFakersStrategyOldOld strategy = new SimpleIntegerFakersStrategyOldOld();
		Object result = strategy.execute(new Faker(), properties);

		assertThat(result).isInstanceOf(Integer.class);
	}
}
