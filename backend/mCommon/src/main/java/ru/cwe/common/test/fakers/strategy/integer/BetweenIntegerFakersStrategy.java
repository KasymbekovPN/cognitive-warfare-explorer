package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersProperty;
import ru.cwe.common.test.fakers.strategy.FakersStrategy;

import java.util.EnumMap;

public final class BetweenIntegerFakersStrategy implements FakersStrategy {

	@Override
	public Object execute(Faker faker, EnumMap<FakersProperty, Object> properties) {
		if (properties.containsKey(FakersProperty.OPERATION) &&
			properties.get(FakersProperty.OPERATION).equals(FakersProperty.BETWEEN) &&
			properties.containsKey(FakersProperty.MIN) &&
			properties.containsKey(FakersProperty.MAX)) {

			Integer min = (Integer) properties.get(FakersProperty.MIN);
			Integer max = (Integer) properties.get(FakersProperty.MAX);
			return faker.number().numberBetween(min, max);
		}
		return null;
	}
}
