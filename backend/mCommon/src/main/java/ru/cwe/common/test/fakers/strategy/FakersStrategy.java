package ru.cwe.common.test.fakers.strategy;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersProperties;
import ru.cwe.common.test.fakers.FakersProperty;

import java.util.EnumMap;

public interface FakersStrategy {
	Object execute(Faker faker, FakersProperties properties);
}
