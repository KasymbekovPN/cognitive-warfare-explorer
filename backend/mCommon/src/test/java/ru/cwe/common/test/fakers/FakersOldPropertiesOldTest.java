package ru.cwe.common.test.fakers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.cwe.common.reflection.Reflections;
import ru.cwe.common.test.fakers.exception.NoSuchFakersProperty;

import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class FakersOldPropertiesOldTest {

	@SneakyThrows
	@Test
	void shouldCheckPutting() {
		EnumMap<FakersPropertyOld, Object> expected = new EnumMap<>(FakersPropertyOld.class);
		FakersPropertiesOld fakersPropertiesOld = new FakersPropertiesOld();

		for (FakersPropertyOld value : FakersPropertyOld.values()) {
			expected.put(value, value);
			fakersPropertiesOld.put(value, value);
		}

		@SuppressWarnings("unchecked")
		EnumMap<FakersPropertyOld, Object> properties = (EnumMap<FakersPropertyOld, Object>) Reflections.get(fakersPropertiesOld, "properties");

		assertThat(properties).isEqualTo(expected);
	}

	@Test
	void shouldCheckBacking() {
		FakersOld expected = new FakersOld();
		FakersOld fakersOld = new FakersPropertiesOld(expected).back();

		assertThat(fakersOld).isEqualTo(expected);
	}

	@Test
	void shouldCheckGetting_ifItAbsence() {
		Throwable throwable = catchThrowable(() -> {
			Object result = new FakersPropertiesOld().get(FakersPropertyOld.OPERATION);
		});
		assertThat(throwable).isInstanceOf(NoSuchFakersProperty.class);
	}

	@Test
	void shouldCheckGetting() {
		Class<Integer> expectedType = Integer.class;
		Object result = new FakersPropertiesOld().put(FakersPropertyOld.TYPE, expectedType).get(FakersPropertyOld.TYPE);

		assertThat(result).isEqualTo(expectedType);
	}

	@Test
	void shouldCheckCastGetting_idAbsence() {
		Throwable throwable = catchThrowable(() -> {
			Object result = new FakersPropertiesOld().get(FakersPropertyOld.TYPE, Integer.class);
		});
		assertThat(throwable).isInstanceOf(NoSuchFakersProperty.class);
	}

	@Test
	void shouldCheckCastGetting_ifBadCast() {
		Throwable throwable = catchThrowable(() -> {
			Object result = new FakersPropertiesOld()
				.put(FakersPropertyOld.TYPE, 123.0)
				.get(FakersPropertyOld.TYPE, Integer.class);
		});
		assertThat(throwable).isInstanceOf(ClassCastException.class);
	}

	@Test
	void shouldCheckCastGetting() {
		int expected = 123;
		Object result = new FakersPropertiesOld()
			.put(FakersPropertyOld.TYPE, expected)
			.get(FakersPropertyOld.TYPE, Integer.class);
		assertThat(result).isEqualTo(expected);
	}

	@Test
	void shouldCheckContains_ifAbsence() {
		FakersPropertiesOld properties = new FakersPropertiesOld();
		assertThat(properties.contains(FakersPropertyOld.TYPE)).isFalse();
	}

	@Test
	void shouldCheckContains() {
		FakersPropertiesOld properties = new FakersPropertiesOld().put(FakersPropertyOld.TYPE, Integer.class);
		assertThat(properties.contains(FakersPropertyOld.TYPE)).isTrue();
	}

	@Test
	void shouldCheckEqual_ifAbsence() {
		FakersPropertiesOld properties = new FakersPropertiesOld();
		assertThat(properties.isEqual(FakersPropertyOld.TYPE, Integer.class)).isFalse();
	}

	@Test
	void shouldCheckEqual_ifNotEqual() {
		FakersPropertiesOld properties = new FakersPropertiesOld().put(FakersPropertyOld.TYPE, Float.class);
		assertThat(properties.isEqual(FakersPropertyOld.TYPE, Integer.class)).isFalse();
	}

	@Test
	void shouldCheckEqual() {
		FakersPropertiesOld properties = new FakersPropertiesOld().put(FakersPropertyOld.TYPE, Integer.class);
		assertThat(properties.isEqual(FakersPropertyOld.TYPE, Integer.class)).isTrue();
	}

	@Test
	void showToString() {
		FakersPropertiesOld properties = new FakersPropertiesOld()
			.put(FakersPropertyOld.TYPE, Integer.class)
			.put(FakersPropertyOld.OPERATION, FakersPropertyOld.BETWEEN)
			.put(FakersPropertyOld.MIN, 0)
			.put(FakersPropertyOld.MAX, 100);
		System.out.println(properties);
	}
}
