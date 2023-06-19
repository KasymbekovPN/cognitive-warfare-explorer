package ru.cwe.common.test.fakers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.cwe.common.test.fakers.exception.NoSuchFakersStrategyException;
import ru.cwe.common.test.fakers.strategy.FakersStrategyOld;

import java.util.HashMap;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class FakersOldTest {

	@Test
	void shouldCheckMaking_ifStrategyNotExist() {
		FakersOld fakersOld = new FakersOld();
		Throwable throwable = catchThrowable(() -> {
			Object result = fakersOld.props(WithoutStrategy.class).back().make();
		});

		assertThat(throwable).isInstanceOf(NoSuchFakersStrategyException.class);
	}

	@Test
	void shouldCheckMaking() {
		int expectedResult = new Random().nextInt();
		HashMap<Class<?>, FakersStrategyOld> strategies = new HashMap<>(){{
			put(Integer.class, createTestIntStrategy(expectedResult));
		}};
		Object result = new FakersOld(strategies).props(Integer.class).back().make();
		assertThat(expectedResult).isEqualTo(result);
	}

	private FakersStrategyOld createTestIntStrategy(Integer result){
		FakersStrategyOld strategy = Mockito.mock(FakersStrategyOld.class);
		Mockito
			.when(strategy.execute(Mockito.anyObject(), Mockito.anyObject()))
			.thenReturn(result);

		return strategy;
	}

	private static class WithoutStrategy {}
}
