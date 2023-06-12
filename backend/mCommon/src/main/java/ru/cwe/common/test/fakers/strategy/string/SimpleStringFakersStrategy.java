package ru.cwe.common.test.fakers.strategy.string;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersProperties;
import ru.cwe.common.test.fakers.FakersProperty;
import ru.cwe.common.test.fakers.strategy.FakersStrategy;

public final class SimpleStringFakersStrategy implements FakersStrategy {
	public static final String PREFIX = "str";

	@Override
	public Object execute(Faker faker, FakersProperties properties) {
		if (properties.isEqual(FakersProperty.OPERATION, FakersProperty.SIMPLE)){
			return String.format("%s%s", PREFIX, faker.number().randomDigit());
		}
		return null;
	}
}
