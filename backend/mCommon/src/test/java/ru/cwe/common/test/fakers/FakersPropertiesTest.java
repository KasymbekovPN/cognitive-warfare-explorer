package ru.cwe.common.test.fakers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.cwe.common.reflection.Reflections;
import ru.cwe.common.test.fakers.exception.NoSuchFakersProperty;

import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class FakersPropertiesTest {

	@SneakyThrows
	@Test
	void shouldCheckPutting() {
		EnumMap<FakersProperty, Object> expected = new EnumMap<>(FakersProperty.class);
		FakersProperties fakersProperties = new FakersProperties();

		for (FakersProperty value : FakersProperty.values()) {
			expected.put(value, value);
			fakersProperties.put(value, value);
		}

		@SuppressWarnings("unchecked")
		EnumMap<FakersProperty, Object> properties = (EnumMap<FakersProperty, Object>) Reflections.get(fakersProperties, "properties");

		assertThat(properties).isEqualTo(expected);
	}

	@Test
	void shouldCheckBacking() {
		Fakers expected = new Fakers();
		Fakers fakers = new FakersProperties(expected).back();

		assertThat(fakers).isEqualTo(expected);
	}

	@Test
	void shouldCheckGetting_ifItAbsence() {
		Throwable throwable = catchThrowable(() -> {
			Object result = new FakersProperties().get(FakersProperty.OPERATION);
		});
		assertThat(throwable).isInstanceOf(NoSuchFakersProperty.class);
	}

	@Test
	void shouldCheckGetting() {
		Class<Integer> expectedType = Integer.class;
		Object result = new FakersProperties().put(FakersProperty.TYPE, expectedType).get(FakersProperty.TYPE);

		assertThat(result).isEqualTo(expectedType);
	}

	@Test
	void shouldCheckCastGetting_idAbsence() {
		Throwable throwable = catchThrowable(() -> {
			Object result = new FakersProperties().get(FakersProperty.TYPE, Integer.class);
		});
		assertThat(throwable).isInstanceOf(NoSuchFakersProperty.class);
	}

	@Test
	void shouldCheckCastGetting_ifBadCast() {
		Throwable throwable = catchThrowable(() -> {
			Object result = new FakersProperties()
				.put(FakersProperty.TYPE, 123.0)
				.get(FakersProperty.TYPE, Integer.class);
		});
		assertThat(throwable).isInstanceOf(ClassCastException.class);
	}

	@Test
	void shouldCheckCastGetting() {
		int expected = 123;
		Object result = new FakersProperties()
			.put(FakersProperty.TYPE, expected)
			.get(FakersProperty.TYPE, Integer.class);
		assertThat(result).isEqualTo(expected);
	}

	@Test
	void shouldCheckContains_ifAbsence() {
		FakersProperties properties = new FakersProperties();
		assertThat(properties.contains(FakersProperty.TYPE)).isFalse();
	}

	@Test
	void shouldCheckContains() {
		FakersProperties properties = new FakersProperties().put(FakersProperty.TYPE, Integer.class);
		assertThat(properties.contains(FakersProperty.TYPE)).isTrue();
	}

	@Test
	void shouldCheckEqual_ifAbsence() {
		FakersProperties properties = new FakersProperties();
		assertThat(properties.isEqual(FakersProperty.TYPE, Integer.class)).isFalse();
	}

	@Test
	void shouldCheckEqual_ifNotEqual() {
		FakersProperties properties = new FakersProperties().put(FakersProperty.TYPE, Float.class);
		assertThat(properties.isEqual(FakersProperty.TYPE, Integer.class)).isFalse();
	}

	@Test
	void shouldCheckEqual() {
		FakersProperties properties = new FakersProperties().put(FakersProperty.TYPE, Integer.class);
		assertThat(properties.isEqual(FakersProperty.TYPE, Integer.class)).isTrue();
	}

	@Test
	void showToString() {
		FakersProperties properties = new FakersProperties()
			.put(FakersProperty.TYPE, Integer.class)
			.put(FakersProperty.OPERATION, FakersProperty.BETWEEN)
			.put(FakersProperty.MIN, 0)
			.put(FakersProperty.MAX, 100);
		System.out.println(properties);
	}
}
