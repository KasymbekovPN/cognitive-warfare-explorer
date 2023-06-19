package ru.cwe.common.test.fakers.strategy.uuid;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersPropertiesOld;
import ru.cwe.common.test.fakers.FakersPropertyOld;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleUuidFakersStrategyOldOldTestOldOld {


	@Test
	void shouldCheckExecution_ifBadProperties() {
		Object result = new SimpleUuidFakersStrategyOldOld().execute(new Faker(), new FakersPropertiesOld());
		assertThat(result).isNull();
	}

	@Test
	void shouldCheckExecution() {
		FakersPropertiesOld properties = new FakersPropertiesOld()
			.put(FakersPropertyOld.OPERATION, FakersPropertyOld.SIMPLE);
		Object result = new SimpleUuidFakersStrategyOldOld().execute(new Faker(), properties);
		assertThat(result).isInstanceOf(UUID.class);

	}
}
