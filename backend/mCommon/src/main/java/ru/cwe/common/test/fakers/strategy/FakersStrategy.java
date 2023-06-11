package ru.cwe.common.test.fakers.strategy;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersProperty;

import java.util.EnumMap;

public interface FakersStrategy {

	// TODO: 11.06.2023 use FakersProperties instead map
	Object execute(Faker faker, EnumMap<FakersProperty, Object> properties);
}
