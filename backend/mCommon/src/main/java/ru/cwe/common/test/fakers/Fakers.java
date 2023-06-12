package ru.cwe.common.test.fakers;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.exception.NoSuchFakersStrategyException;
import ru.cwe.common.test.fakers.strategy.FakersStrategy;
import ru.cwe.common.test.fakers.strategy.integer.IntegerFakersStrategy;

import java.util.HashMap;
import java.util.Map;

public final class Fakers {

	private static final Map<Class<?>, FakersStrategy> DEFAULT_STRATEGIES = new HashMap<>(){{
		put(Integer.class, new IntegerFakersStrategy());
	}};

	private final Faker faker = new Faker();

	private final Map<Class<?>, FakersStrategy> strategies;
	private FakersProperties properties;

	public Fakers() {
		this.strategies = DEFAULT_STRATEGIES;
	}

	public Fakers(Map<Class<?>, FakersStrategy> strategies) {
		this.strategies = DEFAULT_STRATEGIES;
		this.strategies.putAll(strategies);
	}

	public FakersProperties props(Class<?> type){
		return new FakersProperties(this).put(FakersProperty.TYPE, type);
	}

	public Object make(){
		Class<?> key = (Class<?>) properties.get(FakersProperty.TYPE);
		if (strategies.containsKey(key)){
			return strategies.get(key).execute(faker, properties);
		}
		throw new NoSuchFakersStrategyException(String.format("Strategy for %s is absence", key));
	}

	public void set(FakersProperties properties){
		this.properties = properties;
	}
}
