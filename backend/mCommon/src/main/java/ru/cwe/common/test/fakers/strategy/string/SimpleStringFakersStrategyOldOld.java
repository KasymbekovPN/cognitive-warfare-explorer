package ru.cwe.common.test.fakers.strategy.string;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersPropertiesOld;
import ru.cwe.common.test.fakers.FakersPropertyOld;
import ru.cwe.common.test.fakers.strategy.FakersStrategyOld;

// TODO: 18.06.2023 del
public final class SimpleStringFakersStrategyOldOld implements FakersStrategyOld {
	public static final String PREFIX = "str";

	@Override
	public Object execute(Faker faker, FakersPropertiesOld properties) {
		if (properties.isEqual(FakersPropertyOld.OPERATION, FakersPropertyOld.SIMPLE)){
			return String.format("%s%s", PREFIX, faker.number().randomDigit());
		}
		return null;
	}
}
