package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersProperty;
import ru.cwe.common.test.fakers.strategy.FakersStrategy;

import java.util.EnumMap;

public final class LessThanIntegerFakersStrategy implements FakersStrategy {
	@Override
	public Object execute(Faker faker, EnumMap<FakersProperty, Object> properties) {
		if (properties.containsKey(FakersProperty.OPERATION) &&
			properties.get(FakersProperty.OPERATION).equals(FakersProperty.LESS) &&
			properties.containsKey(FakersProperty.THRESHOLD)){

			Integer threshold = (Integer) properties.get(FakersProperty.THRESHOLD);
			return faker.number().numberBetween(Integer.MIN_VALUE, threshold);
		}
		return null;
	}
}
