package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersProperty;
import ru.cwe.common.test.fakers.exception.BadFakersStrategyPropertiesException;
import ru.cwe.common.test.fakers.strategy.FakersStrategy;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public final class IntegerFakersStrategy implements FakersStrategy {
	private static final List<FakersStrategy> STRATEGIES = new ArrayList<>(){{
		add(new SimpleIntegerFakersStrategy());
		add(new BetweenIntegerFakersStrategy());
		add(new LessThanIntegerFakersStrategy());
		add(new GreaterThanIntegerFakersStrategy());
	}};

	@Override
	public Object execute(Faker faker, EnumMap<FakersProperty, Object> properties) {
		for (FakersStrategy strategy : STRATEGIES) {
			Object result = strategy.execute(faker, properties);
			if (result != null){
				return result;
			}
		}

		// TODO: 11.06.2023 take message from properties
		throw new BadFakersStrategyPropertiesException("");
	}
}
