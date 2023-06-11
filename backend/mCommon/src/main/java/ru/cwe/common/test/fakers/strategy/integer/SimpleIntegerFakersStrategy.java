package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersProperty;
import ru.cwe.common.test.fakers.strategy.FakersStrategy;

import java.util.EnumMap;

public final class SimpleIntegerFakersStrategy implements FakersStrategy {
	@Override
	public Object execute(Faker faker, EnumMap<FakersProperty, Object> properties) {
		if (properties.containsKey(FakersProperty.OPERATION) &&
			properties.get(FakersProperty.OPERATION).equals(FakersProperty.SIMPLE)) {
			return faker.number().randomDigit();
		}
		return null;
	}
}
