package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersProperties;
import ru.cwe.common.test.fakers.FakersProperty;
import ru.cwe.common.test.fakers.strategy.FakersStrategy;

public final class GreaterThanIntegerFakersStrategy implements FakersStrategy {
	@Override
	public Object execute(Faker faker, FakersProperties properties) {
		if (properties.isEqual(FakersProperty.OPERATION, FakersProperty.GREATER) &&
			properties.contains(FakersProperty.THRESHOLD)){
			Integer threshold = properties.get(FakersProperty.THRESHOLD, Integer.class);
			return faker.number().numberBetween(threshold + 1, Integer.MAX_VALUE);
		}
		return null;
	}
}
