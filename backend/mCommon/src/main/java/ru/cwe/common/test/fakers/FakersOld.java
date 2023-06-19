package ru.cwe.common.test.fakers;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.exception.NoSuchFakersStrategyException;
import ru.cwe.common.test.fakers.strategy.FakersStrategyOld;
import ru.cwe.common.test.fakers.strategy.integer.IntegerFakersStrategyOldOld;
import ru.cwe.common.test.fakers.strategy.string.StringFakersStrategyOldOld;
import ru.cwe.common.test.fakers.strategy.uuid.UuidFakersStrategyOldOld;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// TODO: 18.06.2023 del
public final class FakersOld {

	private static final Map<Class<?>, FakersStrategyOld> DEFAULT_STRATEGIES = new HashMap<>(){{
		put(Integer.class, new IntegerFakersStrategyOldOld());
		put(String.class, new StringFakersStrategyOldOld());
		put(UUID.class, new UuidFakersStrategyOldOld());
	}};

	private final Faker faker = new Faker();

	private final Map<Class<?>, FakersStrategyOld> strategies;
	private FakersPropertiesOld properties;

	public FakersOld() {
		this.strategies = DEFAULT_STRATEGIES;
	}

	public FakersOld(Map<Class<?>, FakersStrategyOld> strategies) {
		this.strategies = DEFAULT_STRATEGIES;
		this.strategies.putAll(strategies);
	}

	public FakersPropertiesOld props(Class<?> type){
		return new FakersPropertiesOld(this).put(FakersPropertyOld.TYPE, type);
	}

	public Object make(){
		Class<?> key = (Class<?>) properties.get(FakersPropertyOld.TYPE);
		if (strategies.containsKey(key)){
			return strategies.get(key).execute(faker, properties);
		}
		throw new NoSuchFakersStrategyException(String.format("Strategy for %s is absence", key));
	}

	public void set(FakersPropertiesOld properties){
		this.properties = properties;
	}
}
