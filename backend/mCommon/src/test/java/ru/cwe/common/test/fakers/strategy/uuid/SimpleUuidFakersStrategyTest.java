package ru.cwe.common.test.fakers.strategy.uuid;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.cwe.common.test.fakers.FakersProperties;
import ru.cwe.common.test.fakers.FakersProperty;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleUuidFakersStrategyTest {


	@Test
	void shouldCheckExecution_ifBadProperties() {
		Object result = new SimpleUuidFakersStrategy().execute(new Faker(), new FakersProperties());
		assertThat(result).isNull();
	}

	@Test
	void shouldCheckExecution() {
		FakersProperties properties = new FakersProperties()
			.put(FakersProperty.OPERATION, FakersProperty.SIMPLE);
		Object result = new SimpleUuidFakersStrategy().execute(new Faker(), properties);
		assertThat(result).isInstanceOf(UUID.class);

	}
}
