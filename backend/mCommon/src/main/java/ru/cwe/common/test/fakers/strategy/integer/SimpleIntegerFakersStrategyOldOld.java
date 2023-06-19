package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersPropertiesOld;
import ru.cwe.common.test.fakers.FakersPropertyOld;
import ru.cwe.common.test.fakers.strategy.FakersStrategyOld;

// TODO: 18.06.2023 del
public final class SimpleIntegerFakersStrategyOldOld implements FakersStrategyOld {
	@Override
	public Object execute(Faker faker, FakersPropertiesOld properties) {
		if (properties.isEqual(FakersPropertyOld.OPERATION, FakersPropertyOld.SIMPLE)){
			return faker.number().randomDigit();
		}
		return null;
	}
}
