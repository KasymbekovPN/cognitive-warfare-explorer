package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersProperties;
import ru.cwe.common.test.fakers.FakersProperty;
import ru.cwe.common.test.fakers.strategy.FakersStrategy;

public final class LessThanIntegerFakersStrategy implements FakersStrategy {
	@Override
	public Object execute(Faker faker, FakersProperties properties) {
		if (properties.isEqual(FakersProperty.OPERATION, FakersProperty.LESS) &&
			properties.contains(FakersProperty.THRESHOLD)){
			Integer threshold = properties.get(FakersProperty.THRESHOLD, Integer.class);

			return faker.number().numberBetween(Integer.MIN_VALUE, threshold);
		}
		return null;
	}
}
