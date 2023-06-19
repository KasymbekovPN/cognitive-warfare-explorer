package ru.cwe.common.test.fakers.strategy.uuid;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersPropertiesOld;
import ru.cwe.common.test.fakers.FakersPropertyOld;
import ru.cwe.common.test.fakers.strategy.FakersStrategyOld;

import java.util.UUID;

// TODO: 18.06.2023 del
public final class SimpleUuidFakersStrategyOldOld implements FakersStrategyOld {
	@Override
	public Object execute(Faker faker, FakersPropertiesOld properties) {
		if (properties.isEqual(FakersPropertyOld.OPERATION, FakersPropertyOld.SIMPLE)){
			return UUID.randomUUID();
		}
		return null;
	}
}
