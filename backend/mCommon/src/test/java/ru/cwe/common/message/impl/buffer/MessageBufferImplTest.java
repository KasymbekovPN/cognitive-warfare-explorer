package ru.cwe.common.message.impl.buffer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.cwe.common.message.api.message.Message;
import ru.cwe.common.record.api.Record;
import ru.cwe.common.reflection.Reflections;
import ru.cwe.common.test.fakers.Fakers;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

class MessageBufferImplTest {

	@Test
	void shouldCheckCreation(){
		TestExecutorService es = new TestExecutorService();
		MessageBufferImpl<Record> instance = MessageBufferImpl.instance(
			new ArrayBlockingQueue<>(10),
			es,
			null,
			Fakers.str_().random()
		);

		assertThat(instance).isNotNull();
		assertThat(es.isTaskCalled()).isTrue();
	}

	@SneakyThrows
	@Test
	void shouldCheckShutdown(){
		TestExecutorService es = new TestExecutorService();
		MessageBufferImpl<Record> instance = MessageBufferImpl.instance(
			new ArrayBlockingQueue<>(10),
			es,
			null,
			Fakers.str_().random()
		);
		instance.shutdown();

		AtomicBoolean started = Reflections.get(instance, "started", AtomicBoolean.class);
		assertThat(started.get()).isFalse();
		assertThat(es.isShutdownCalled()).isTrue();
	}

	@SneakyThrows
	@Test
	void shouldCheckExecution(){
		TestAction action = new TestAction();
		MessageBufferImpl<TestRecord> instance = MessageBufferImpl.instance(
			new ArrayBlockingQueue<TestRecord>(10),
			Executors.newFixedThreadPool(1),
			action,
			Fakers.str_().random()
		);

		UUID key = Fakers.uuid_().random();
		boolean result = instance.offer(new TestRecord(key));

		Thread.sleep(1);

		assertThat(result).isTrue();
		assertThat(action.getKey()).isEqualTo(key);
	}

	@Test
	void shouldCheckFailOffer(){
		int capacity = 3;
		MessageBufferImpl<TestRecord> instance = MessageBufferImpl.instance(
			new ArrayBlockingQueue<TestRecord>(capacity),
			Executors.newFixedThreadPool(1),
			new LongTestAction(),
			Fakers.str_().random()
		);

		for (int i = 0; i <= capacity; i++) {
			assertThat(instance.offer(new TestRecord(UUID.randomUUID()))).isTrue();
		}
		assertThat(instance.offer(new TestRecord(UUID.randomUUID()))).isFalse();
	}

	@RequiredArgsConstructor
	@EqualsAndHashCode
	private static class TestRecord implements Record {
		private final UUID key;

		@Override
		public UUID key() {return key;}
		@Override
		public Message value() {return null;}
		@Override
		public <T> T get(String property, Class<T> type) {return null;}
	}

	private static class LongTestAction implements Consumer<TestRecord> {
		@Override
		public void accept(TestRecord record) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static class TestAction implements Consumer<TestRecord>{
		@Getter private UUID key;

		@Override
		public void accept(TestRecord record) {
			this.key = record.key;
		}
	}

	private static class TestExecutorService implements ExecutorService {
		@Getter private boolean shutdownCalled;
		@Getter private boolean taskCalled;

		@Override
		public Future<?> submit(Runnable task) {
			this.taskCalled = true;
			return null;
		}

		@Override
		public void shutdown() {
			this.shutdownCalled = true;
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
