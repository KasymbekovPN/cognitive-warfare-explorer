package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersProperties;
import ru.cwe.common.test.fakers.FakersProperty;
import ru.cwe.common.test.fakers.strategy.FakersStrategy;

public final class SimpleIntegerFakersStrategy implements FakersStrategy {
	@Override
	public Object execute(Faker faker, FakersProperties properties) {
		if (properties.isEqual(FakersProperty.OPERATION, FakersProperty.SIMPLE)){
			return faker.number().randomDigit();
		}
		return null;
	}
}
