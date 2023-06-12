package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersProperties;
import ru.cwe.common.test.fakers.FakersProperty;
import ru.cwe.common.test.fakers.strategy.FakersStrategy;

public final class BetweenIntegerFakersStrategy implements FakersStrategy {
	@Override
	public Object execute(Faker faker, FakersProperties properties) {
		if (properties.isEqual(FakersProperty.OPERATION, FakersProperty.BETWEEN) &&
			properties.contains(FakersProperty.MIN) &&
			properties.contains(FakersProperty.MAX)){

			Integer min = properties.get(FakersProperty.MIN, Integer.class);
			Integer max = properties.get(FakersProperty.MAX, Integer.class);
			return faker.number().numberBetween(min, max);
		}
		return null;
	}
}
