package ru.cwe.common.test.fakers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.cwe.common.test.fakers.exception.NoSuchFakersStrategyException;
import ru.cwe.common.test.fakers.strategy.FakersStrategy;

import java.util.HashMap;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class FakersTest {

	@Test
	void shouldCheckMaking_ifStrategyNotExist() {
		Fakers fakers = new Fakers();
		Throwable throwable = catchThrowable(() -> {
			Object result = fakers.props(WithoutStrategy.class).back().make();
		});

		assertThat(throwable).isInstanceOf(NoSuchFakersStrategyException.class);
	}

	@Test
	void shouldCheckMaking() {
		int expectedResult = new Random().nextInt();
		HashMap<Class<?>, FakersStrategy> strategies = new HashMap<>(){{
			put(Integer.class, createTestIntStrategy(expectedResult));
		}};
		Object result = new Fakers(strategies).props(Integer.class).back().make();
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
