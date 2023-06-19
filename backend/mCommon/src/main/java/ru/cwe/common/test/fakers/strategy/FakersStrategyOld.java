package ru.cwe.common.test.fakers.strategy;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersPropertiesOld;

// TODO: 18.06.2023 del
public interface FakersStrategyOld {
	Object execute(Faker faker, FakersPropertiesOld properties);
}
