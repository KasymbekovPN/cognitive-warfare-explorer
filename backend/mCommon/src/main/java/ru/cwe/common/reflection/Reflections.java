package ru.cwe.common.reflection;

import java.lang.reflect.Field;

public final class Reflections {

	public static Object get(final Object object, final String name) throws Exception {
		Field field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		Object value = field.get(object);
		field.setAccessible(false);

		return value;
	}

	public static <T> T get(final Object object, final String name, final Class<T> type) throws Exception {
		Object rawObject = get(object, name);
		return type.cast(rawObject);
	}
}
