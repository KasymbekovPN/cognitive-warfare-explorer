package ru.cwe.common.test.fakers;

import com.github.javafaker.Faker;
import ru.cwe.common.test.fakers.exception.NoSuchFakersStrategyException;
import ru.cwe.common.test.fakers.strategy.FakersStrategy;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public final class Fakers {

	private static final Map<Class<?>, FakersStrategy> DEFAULT_STRATEGIES = new HashMap<>(){{
//		put(Integer.class, new IntegerFakersStrategy());
	}};

	private final Faker faker = new Faker();

	private Map<Class<?>, FakersStrategy> strategies;
	private EnumMap<FakersProperty, Object> properties;

	public Fakers() {
		this.strategies = DEFAULT_STRATEGIES;
	}

	public Fakers(Map<Class<?>, FakersStrategy> strategies) {
		this.strategies = DEFAULT_STRATEGIES;
		this.strategies.putAll(strategies);
	}

	public PropertiesCollector fake(Class<?> type){
		return new PropertiesCollector(this, type);
	}

	public Object make(){
		Class<?> key = (Class<?>) properties.get(FakersProperty.TYPE);
		if (strategies.containsKey(key)){
			return strategies.get(key).execute(faker, properties);
		}
		throw new NoSuchFakersStrategyException(String.format("Strategy for %s is absence", key));
	}

	private void set(EnumMap<FakersProperty, Object> properties){
		this.properties = properties;
	}

	public static final class PropertiesCollector {

		private final EnumMap<FakersProperty, Object> properties;
		private final Fakers fakers;

		public PropertiesCollector(Fakers fakers, Class<?> type) {
			this.fakers = fakers;
			this.properties = new EnumMap<>(FakersProperty.class);
			this.properties.put(FakersProperty.TYPE, type);
		}

		public PropertiesCollector add(FakersProperty property, Object value){
			if (!property.equals(FakersProperty.TYPE)){
				properties.put(property, value);
			}
			return this;
		}

		public Fakers set(){
			fakers.set(properties);
			return fakers;
		}
	}
}
