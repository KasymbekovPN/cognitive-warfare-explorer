package utils;

import java.lang.reflect.Field;

public class Reflections {

	public static Object getFieldValue(Object object, String name) throws Exception {
		Field field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		Object value = field.get(object);
		field.setAccessible(false);

		return value;
	}

	public static <T> T getFieldValue(Object object, String name, Class<T> type) throws Exception{
		Object rawObject = getFieldValue(object, name);
		return type.cast(rawObject);
	}
}
