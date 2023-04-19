package ru.cwe.conversation.tube.listener;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class TotalCounterTubeListenerTest {

	@Test
	void shouldCheckInitialGetting() {
		TotalCounterTubeListener listener = new TotalCounterTubeListener();

		assertThat(listener.getCounter().get()).isZero();
	}

	@SneakyThrows
	@Test
	void shouldCheckGetting() {
		int expectedCounter = 10;
		int threadAmount = 5;
		TotalCounterTubeListener listener = new TotalCounterTubeListener();

		ArrayList<Thread> threads = new ArrayList<>();
		for (int i = 0; i < threadAmount; i++) {
			threads.add(new Thread(() -> {
				for (int y = 0; y < expectedCounter; y++) {
					listener.listen(null);
				}
			}));
		}

		for (Thread thread : threads) {
			thread.start();
		}
		Thread.sleep(10);

		assertThat(listener.getCounter().get()).isEqualTo(expectedCounter * threadAmount);
	}
}
