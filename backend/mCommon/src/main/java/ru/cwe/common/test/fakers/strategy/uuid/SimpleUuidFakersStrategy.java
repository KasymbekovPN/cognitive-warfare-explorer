package ru.cwe.common.test.fakers.strategy.uuid;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersProperties;
import ru.cwe.common.test.fakers.FakersProperty;
import ru.cwe.common.test.fakers.strategy.FakersStrategy;

import java.util.UUID;

public final class SimpleUuidFakersStrategy implements FakersStrategy {
	@Override
	public Object execute(Faker faker, FakersProperties properties) {
		if (properties.isEqual(FakersProperty.OPERATION, FakersProperty.SIMPLE)){
			return UUID.randomUUID();
		}
		return null;
	}
}
