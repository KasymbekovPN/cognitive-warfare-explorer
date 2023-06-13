package ru.cwe.common.listener;

import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class RestartableListenerImplTest {

	@Test
	void shouldCheckStart_ifSupplierGetNull() {
		Supplier<ListenerThread> threadSupplier = () -> { return null; };
		Throwable throwable = catchThrowable(() -> {
			new RestartableListenerImpl(threadSupplier).start();
		});
		assertThat(throwable).isNull();
	}

	@Test
	void shouldCheckStart() {
		TestThreadSupplier supplier = new TestThreadSupplier();
		RestartableListenerImpl listener = new RestartableListenerImpl(supplier);

		int startAttempts = 10;
		for (int i = 0; i < startAttempts; i++) {
			listener.start();
		}

		assertThat(supplier.getCounter()).isEqualTo(1);
		assertThat(supplier.getThread().getStartCounter()).isEqualTo(1);
	}

	@Test
	void shouldCheckShutdown() {
		TestThreadSupplier supplier = new TestThreadSupplier();
		RestartableListenerImpl listener = new RestartableListenerImpl(supplier);
		listener.start();
		listener.shutdown();

		assertThat(supplier.getCounter()).isEqualTo(1);
		assertThat(supplier.getThread().getShutdownCounter()).isEqualTo(1);
	}

	@Getter
	private static class TestListenerThread implements ListenerThread {
		private int startCounter;
		private int shutdownCounter;
		@Override
		public void start() { this.startCounter++; }
		@Override
		public void shutdown() { this.shutdownCounter++; }
	}

	@Getter
	private static class TestThreadSupplier implements Supplier<ListenerThread>{
		private final TestListenerThread thread = new TestListenerThread();
		private int counter;
		@Override
		public ListenerThread get() {
			this.counter++;
			return thread;
		}
	}
}
