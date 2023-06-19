package ru.cwe.common.test.fakers.strategy.integer;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.FakersPropertiesOld;
import ru.cwe.common.test.fakers.exception.BadFakersStrategyPropertiesException;
import ru.cwe.common.test.fakers.strategy.FakersStrategyOld;

import java.util.ArrayList;
import java.util.List;

// TODO: 18.06.2023 del
public final class IntegerFakersStrategyOldOld implements FakersStrategyOld {
	private static final List<FakersStrategyOld> STRATEGIES = new ArrayList<>(){{
		add(new SimpleIntegerFakersStrategyOldOld());
		add(new BetweenIntegerFakersStrategyOldOld());
		add(new LessThanIntegerFakersStrategyOldOld());
		add(new GreaterThanIntegerFakersStrategyOldOld());
	}};

	@Override
	public Object execute(Faker faker, FakersPropertiesOld properties) {
		for (FakersStrategyOld strategy : STRATEGIES) {
			Object result = strategy.execute(faker, properties);
			if (result != null){
				return result;
			}
		}

		throw new BadFakersStrategyPropertiesException(properties.toString());
	}
}
