package ru.cwe.common.test.fakers;

import ru.cwe.common.test.fakers.exception.NoSuchFakersProperty;

import java.util.EnumMap;

// TODO: 18.06.2023 del
public final class FakersPropertiesOld {
	private final EnumMap<FakersPropertyOld, Object> properties = new EnumMap<>(FakersPropertyOld.class);
	private final FakersOld fakersOld;

	public FakersPropertiesOld() {
		this.fakersOld = new FakersOld();
	}

	public FakersPropertiesOld(FakersOld fakersOld) {
		this.fakersOld = fakersOld;
	}

	public FakersPropertiesOld put(FakersPropertyOld property, Object value){
		properties.put(property, value);
		return this;
	}

	public FakersOld back(){
		fakersOld.set(this);
		return fakersOld;
	}

	public Object get(FakersPropertyOld property){
		if (properties.containsKey(property)){
			return properties.get(property);
		}
		throw new NoSuchFakersProperty(property.getValue());
	}

	public <T> T get(FakersPropertyOld property, Class<T> type){
		if (properties.containsKey(property)){
			return type.cast(properties.get(property));
		}
		throw new NoSuchFakersProperty(property.getValue());
	}

	public boolean contains(FakersPropertyOld property){
		return properties.containsKey(property);
	}

	public boolean isEqual(FakersPropertyOld property, Object value){
		return properties.containsKey(property) && properties.get(property).equals(value);
	}

	@Override
	public String toString() {
		return "FakersProperties{" +
			"properties=" + properties +
			", fakers=" + fakersOld +
			'}';
	}
}
