package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersPropertiesOld;
import ru.cwe.common.test.fakers.FakersPropertyOld;
import ru.cwe.common.test.fakers.strategy.FakersStrategyOld;

// TODO: 18.06.2023 del
public final class BetweenIntegerFakersStrategyOldOld implements FakersStrategyOld {
	@Override
	public Object execute(Faker faker, FakersPropertiesOld properties) {
		if (properties.isEqual(FakersPropertyOld.OPERATION, FakersPropertyOld.BETWEEN) &&
			properties.contains(FakersPropertyOld.MIN) &&
			properties.contains(FakersPropertyOld.MAX)){

			Integer min = properties.get(FakersPropertyOld.MIN, Integer.class);
			Integer max = properties.get(FakersPropertyOld.MAX, Integer.class);
			return faker.number().numberBetween(min, max);
		}
		return null;
	}
}
