package ru.cwe.common.reflection;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.cwe.common.reflection.Reflections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ReflectionsTest {
	private static final int EXPECTED_INT_VALUE = 123;

	@Test
	void shouldCheckRawGetting_ifFieldAbsent() throws Exception {
		TestClass object = new TestClass();
		Throwable throwable = catchThrowable(() -> {
			Reflections.get(object, "absentFieldName");
		});

		assertThat(throwable).isInstanceOf(NoSuchFieldException.class);
	}

	@SneakyThrows
	@Test
	void shouldCheckRawGetting() {
		TestClass object = new TestClass();
		object.setIntValue(EXPECTED_INT_VALUE);

		Object result = Reflections.get(object, "intValue");
		assertThat(result).isEqualTo(EXPECTED_INT_VALUE);
	}

	@Test
	void shouldCheckGetting_ifFieldAbsent() {
		TestClass object = new TestClass();
		Throwable throwable = catchThrowable(() -> {
			Reflections.get(object, "absentFieldName", Integer.class);
		});

		assertThat(throwable).isInstanceOf(NoSuchFieldException.class);
	}

	@Test
	void shouldCheckGetting_ifTypeMismatching() {
		TestClass object = new TestClass();
		object.setIntValue(EXPECTED_INT_VALUE);

		Throwable throwable = catchThrowable(() -> {
			Reflections.get(object, "intValue", Reflections.class);
		});

		assertThat(throwable).isInstanceOf(ClassCastException.class);
	}

	@SneakyThrows
	@Test
	void shouldCheckGetting() {
		TestClass object = new TestClass();
		object.setIntValue(EXPECTED_INT_VALUE);

		Integer result = Reflections.get(object, "intValue", Integer.class);
		assertThat(result).isEqualTo(EXPECTED_INT_VALUE);
	}

	@Setter
	@Getter
	private static class TestClass {
		private Integer intValue;
	}
}
