package ru.cwe.common.test.fakers.strategy.string;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersProperties;
import ru.cwe.common.test.fakers.exception.BadFakersStrategyPropertiesException;
import ru.cwe.common.test.fakers.strategy.FakersStrategy;

import java.util.ArrayList;
import java.util.List;

public final class StringFakersStrategy implements FakersStrategy {
	private static final List<FakersStrategy> STRATEGIES = new ArrayList<FakersStrategy>(){{
		add(new SimpleStringFakersStrategy());
	}};

	@Override
	public Object execute(Faker faker, FakersProperties properties) {
		for (FakersStrategy strategy : STRATEGIES) {
			Object result = strategy.execute(faker, properties);
			if (result != null){
				return result;
			}
		}

		throw new BadFakersStrategyPropertiesException(properties.toString());
	}
}
