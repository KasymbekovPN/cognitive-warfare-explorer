package ru.cwe.common.test.fakers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.cwe.common.reflection.Reflections;
import ru.cwe.common.test.fakers.exception.NoSuchFakersStrategyException;
import ru.cwe.common.test.fakers.strategy.FakersStrategy;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class FakersTest {

	@SneakyThrows
	@Test
	void shouldCheckPropertiesCollectorAddition() {
		Class<Integer> type = Integer.class;
		Fakers.PropertiesCollector collector = new Fakers.PropertiesCollector(null, type);

		HashSet<FakersProperty> set = new HashSet<>() {{
			add(FakersProperty.OPERATION);
			add(FakersProperty.SIMPLE);
		}};

		collector.add(FakersProperty.TYPE, String.class);
		for (FakersProperty property : set) {
			collector.add(property, property);
		}

		@SuppressWarnings("unchecked")
		EnumMap<FakersProperty, Object> properties = (EnumMap<FakersProperty, Object>) Reflections.get(collector, "properties");
		assertThat(properties.get(FakersProperty.TYPE)).isEqualTo(type);
		for (FakersProperty key : set) {
			assertThat(properties.get(key)).isEqualTo(key);
		}
	}

	@SneakyThrows
	@Test
	void shouldCheckPropertiesCollectorSetting() {
		Fakers expectedFakers = new Fakers();
		Class<Integer> type = Integer.class;
		Fakers fakers = new Fakers.PropertiesCollector(expectedFakers, type).set();

		assertThat(fakers).isEqualTo(expectedFakers);

		@SuppressWarnings("unchecked")
		EnumMap<FakersProperty, Object> properties = (EnumMap<FakersProperty, Object>) Reflections.get(fakers, "properties");
		assertThat(properties.get(FakersProperty.TYPE)).isEqualTo(type);
	}

	@SneakyThrows
	@Test
	void shouldCheckFakeMethod() {
		Class<Integer> expectedType = Integer.class;
		Fakers.PropertiesCollector collector = new Fakers().fake(expectedType);

		@SuppressWarnings("unchecked")
		EnumMap<FakersProperty, Object> properties = (EnumMap<FakersProperty, Object>) Reflections.get(collector, "properties");
		assertThat(properties.get(FakersProperty.TYPE)).isEqualTo(expectedType);
	}

	@Test
	void shouldCheckMaking_ifStrategyNotExist() {
		Fakers fakers = new Fakers();
		Throwable throwable = catchThrowable(() -> {
			Object result = fakers.fake(WithoutStrategy.class).set().make();
		});

		assertThat(throwable).isInstanceOf(NoSuchFakersStrategyException.class);
	}

	@Test
	void shouldCheckMaking() {
		int expectedResult = new Random().nextInt();
		HashMap<Class<?>, FakersStrategy> strategies = new HashMap<>(){{
			put(Integer.class, createTestIntStrategy(expectedResult));
		}};
		Object result = new Fakers(strategies).fake(Integer.class).set().make();
		assertThat(expectedResult).isEqualTo(result);
	}

	private FakersStrategy createTestIntStrategy(Integer result){
		FakersStrategy strategy = Mockito.mock(FakersStrategy.class);
		Mockito
			.when(strategy.execute(Mockito.anyObject(), Mockito.anyObject()))
			.thenReturn(result);

		return strategy;
	}

	private static class WithoutStrategy {}
}
