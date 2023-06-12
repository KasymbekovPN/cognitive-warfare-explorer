package ru.cwe.common.test.fakers;

import ru.cwe.common.test.fakers.exception.NoSuchFakersProperty;

import java.util.EnumMap;

public final class FakersProperties {
	private final EnumMap<FakersProperty, Object> properties = new EnumMap<>(FakersProperty.class);
	private final Fakers fakers;

	public FakersProperties() {
		this.fakers = new Fakers();
	}

	public FakersProperties(Fakers fakers) {
		this.fakers = fakers;
	}

	public FakersProperties put(FakersProperty property, Object value){
		properties.put(property, value);
		return this;
	}

	public Fakers back(){
		fakers.set(this);
		return fakers;
	}

	public Object get(FakersProperty property){
		if (properties.containsKey(property)){
			return properties.get(property);
		}
		throw new NoSuchFakersProperty(property.getValue());
	}

	public <T> T get(FakersProperty property, Class<T> type){
		if (properties.containsKey(property)){
			return type.cast(properties.get(property));
		}
		throw new NoSuchFakersProperty(property.getValue());
	}

	public boolean contains(FakersProperty property){
		return properties.containsKey(property);
	}

	public boolean isEqual(FakersProperty property, Object value){
		return properties.containsKey(property) && properties.get(property).equals(value);
	}

	@Override
	public String toString() {
		return "FakersProperties{" +
			"properties=" + properties +
			", fakers=" + fakers +
			'}';
	}
}
