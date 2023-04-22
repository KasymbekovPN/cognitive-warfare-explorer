package ru.cwe.conversation.exception;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.cwe.common.reflection.Reflections;
import utils.faker.Fakers;

import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class AbsentFieldRuntimeExceptionBuilderImplTest {
	private static Function<String, RuntimeException> creator;
	private static final RuntimeException exception = new RuntimeException("");

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
		String name0 = Fakers.base().string().string();
		String name1 = Fakers.base().string().string();
		String name2 = Fakers.base().string().string();

		AbsentFieldRuntimeExceptionBuilderImpl builder = new AbsentFieldRuntimeExceptionBuilderImpl(null);
		builder.checkField(name0, null);
		builder.checkField(name1, new Object());
		builder.checkField(name2, null);

		String expected = String.format("Absent fields: %s & %s", name0, name2);
		StringBuilder messageSB = Reflections.get(builder, "messageSB", StringBuilder.class);

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
