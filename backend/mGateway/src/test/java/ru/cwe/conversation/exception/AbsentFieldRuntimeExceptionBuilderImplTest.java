package ru.cwe.conversation.exception;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Reflections;

import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class AbsentFieldRuntimeExceptionBuilderImplTest {
	private static Function<String, RuntimeException> creator;
	private static RuntimeException exception = new RuntimeException("");

	@BeforeAll
	static void beforeAll() {
		creator = new Function<>() {
			@Override
			public RuntimeException apply(String s) {
				return exception;
			}
		};
	}

	@SneakyThrows
	@Test
	void shouldCheckFieldChecking() {
		String name0 = "name0";
		String name1 = "name1";
		String name2 = "name2";

		AbsentFieldRuntimeExceptionBuilderImpl builder = new AbsentFieldRuntimeExceptionBuilderImpl(null);
		builder.checkField(name0, null);
		builder.checkField(name1, new Object());
		builder.checkField(name2, null);

		String expected = String.format("Absent fields: %s & %s", name0, name2);
		StringBuilder messageSB = Reflections.getFieldValue(builder, "messageSB", StringBuilder.class);

		assertThat(messageSB.toString()).isEqualTo(expected);
	}

	@Test
	void shouldCheckBuilding_withoutException() {
		Optional<RuntimeException> maybeException = new AbsentFieldRuntimeExceptionBuilderImpl(creator).build();

		assertThat(maybeException).isEmpty();
	}

	@Test
	void shouldCheckBuilding_withException() {
		Optional<RuntimeException> maybeException = new AbsentFieldRuntimeExceptionBuilderImpl(creator)
			.checkField("name", null)
			.build();

		assertThat(maybeException).isPresent();
		assertThat(maybeException.get()).isEqualTo(exception);
	}
}
