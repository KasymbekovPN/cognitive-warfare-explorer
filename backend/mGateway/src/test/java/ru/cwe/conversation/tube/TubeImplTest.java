package ru.cwe.conversation.tube;

import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.gateway.out.OutGateway;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.utils.reflection.Reflections;
import utils.faker.Fakers;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;

class TubeImplTest {

	@SneakyThrows
	@Test
	void shouldCheckCreation() {
		ArrayBlockingQueue<TubeDatum> queue = new ArrayBlockingQueue<TubeDatum>(100);
		TestExecutorService executorService = new TestExecutorService();
		TestOutGateway outGateway = new TestOutGateway();

		TubeImpl tube = TubeImpl.create(queue, executorService, outGateway);
		assertThat(tube).isNotNull();
		assertThat(executorService.isSubmitted()).isTrue();

		tube.dispose();
	}

	@SneakyThrows
	@Test
	void shouldCheckDispose() {
		ArrayBlockingQueue<TubeDatum> queue = new ArrayBlockingQueue<TubeDatum>(100);
		TestExecutorService executorService = new TestExecutorService();
		TestOutGateway outGateway = new TestOutGateway();

		TubeImpl tube = TubeImpl.create(queue, executorService, outGateway);
		tube.dispose();

		AtomicBoolean inProgress = Reflections.get(tube, "inProcess", AtomicBoolean.class);
		assertThat(inProgress.get()).isFalse();
		assertThat(executorService.isSd()).isTrue();
		assertThat(outGateway.isSd()).isTrue();
	}

	@SneakyThrows
	@Test
	void shouldCheckPutting() {
		ArrayBlockingQueue<TubeDatum> queue = new ArrayBlockingQueue<TubeDatum>(100);
		TestExecutorService executorService = new TestExecutorService();
		TestOutGateway outGateway = new TestOutGateway();

		TubeImpl tube = TubeImpl.create(queue, executorService, outGateway);

		boolean result = tube.put(new TubeDatumImpl(null, Fakers.address().host(), Fakers.address().port()));
		assertThat(result).isTrue();

		tube.dispose();
	}

	@SneakyThrows
	@Test
	void shouldCheckPutting_ifDisposed() {
		ArrayBlockingQueue<TubeDatum> queue = new ArrayBlockingQueue<TubeDatum>(100);
		TestExecutorService executorService = new TestExecutorService();
		TestOutGateway outGateway = new TestOutGateway();

		TubeImpl tube = TubeImpl.create(queue, executorService, outGateway);
		tube.dispose();

		boolean result = tube.put(new TubeDatumImpl(null, Fakers.address().host(), Fakers.address().port()));
		assertThat(result).isFalse();
	}

	@SneakyThrows
	@Test
	void shouldCheckSize() {
		ArrayBlockingQueue<TubeDatum> queue = new ArrayBlockingQueue<TubeDatum>(100);
		TestExecutorService executorService = new TestExecutorService();
		TestOutGateway outGateway = new TestOutGateway() {
			@Override
			public void send(PayloadMessage message, String host, int port) {
				try {
					Thread.sleep(1_000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		};

		TubeImpl tube = TubeImpl.create(queue, executorService, outGateway);

		tube.put(new TubeDatumImpl(null, Fakers.address().host(), Fakers.address().port()));
		tube.put(new TubeDatumImpl(null, Fakers.address().host(), Fakers.address().port()));
		tube.put(new TubeDatumImpl(null, Fakers.address().host(), Fakers.address().port()));

		assertThat(tube.size()).isEqualTo(3);

		tube.dispose();
	}

	@SneakyThrows
	@Test
	void shouldCheckCreatorGetting() {
		ArrayBlockingQueue<TubeDatum> queue = new ArrayBlockingQueue<TubeDatum>(100);
		TestExecutorService executorService = new TestExecutorService();
		TestOutGateway outGateway = new TestOutGateway();

		TubeImpl tube = TubeImpl.create(queue, executorService, outGateway);
		DatumCreator creator = tube.creator();

		assertThat(creator).isInstanceOf(DatumCreatorImpl.class);
		assertThat(Reflections.get(creator, "tube", Tube.class)).isEqualTo(tube);

		tube.dispose();
	}

	@SneakyThrows
	@Test
	void shouldCheckExecution() {
		ArrayBlockingQueue<TubeDatum> queue = new ArrayBlockingQueue<TubeDatum>(100);
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		TestOutGateway outGateway = new TestOutGateway();

		TubeImpl tube = TubeImpl.create(queue, executorService, outGateway);
		int size = 10;
		for (int i = 0; i < size; i++) {
			tube.put(new TubeDatumImpl(null, Fakers.address().host(), Fakers.address().port()));
		}
		Thread.sleep(10);

		assertThat(outGateway.getCounter()).isEqualTo(size);

		tube.dispose();
	}

	private static class TestOutGateway implements OutGateway {
		@Getter
		private boolean sd;
		@Getter
		private int counter = 0;

		@Override
		public void send(PayloadMessage message) {}
		@Override
		public void send(PayloadMessage message, String host, int port) {
			this.counter++;
		}
		@Override
		public void shutdown() {
			this.sd = true;
		}
	}

	private static class TestExecutorService implements ExecutorService{
		@Getter
		private boolean submitted;
		@Getter
		private boolean sd;

		@Override
		public Future<?> submit(Runnable task) {
			this.submitted = true;
			return null;
		}

		@Override
		public void shutdown() {
			this.sd = true;
		}

		@Override
		public List<Runnable> shutdownNow() {return null;}
		@Override
		public boolean isShutdown() {return false;}
		@Override
		public boolean isTerminated() {return false;}
		@Override
		public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {return false;}
		@Override
		public <T> Future<T> submit(Callable<T> task) {return null;}
		@Override
		public <T> Future<T> submit(Runnable task, T result) {return null;}
		@Override
		public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {return null;}
		@Override
		public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {return null;}
		@Override
		public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {return null;}
		@Override
		public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {return null;}
		@Override
		public void execute(Runnable command) {}
	}
}
