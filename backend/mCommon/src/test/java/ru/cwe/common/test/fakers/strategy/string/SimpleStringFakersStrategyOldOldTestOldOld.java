package ru.cwe.common.test.fakers.strategy.string;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersPropertiesOld;
import ru.cwe.common.test.fakers.FakersPropertyOld;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleStringFakersStrategyOldOldTestOldOld {

	@Test
	void shouldCheckExecution_ifBadProperties() {
		Object result = new SimpleStringFakersStrategyOldOld().execute(new Faker(), new FakersPropertiesOld());
		assertThat(result).isNull();
	}

	@Test
	void shouldCheckExecution() {
		FakersPropertiesOld properties = new FakersPropertiesOld()
			.put(FakersPropertyOld.OPERATION, FakersPropertyOld.SIMPLE);
		Object result = new SimpleStringFakersStrategyOldOld().execute(new Faker(), properties);
		assertThat(result).isInstanceOf(String.class);

		String castResult = (String) result;
		assertThat(castResult.substring(0, 3)).isEqualTo(SimpleStringFakersStrategyOldOld.PREFIX);
	}
}
