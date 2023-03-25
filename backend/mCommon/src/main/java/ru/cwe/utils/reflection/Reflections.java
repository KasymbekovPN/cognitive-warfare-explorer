package ru.cwe.utils.reflection;

import java.lang.reflect.Field;

public final class Reflections {

	public static Object get(Object object, String name) throws Exception {
		Field field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		Object value = field.get(object);
		field.setAccessible(false);

		return value;
	}

	public static <T> T get(Object object, String name, Class<T> type) throws Exception {
		Object rawObject = get(object, name);
		return type.cast(rawObject);
	}
}
