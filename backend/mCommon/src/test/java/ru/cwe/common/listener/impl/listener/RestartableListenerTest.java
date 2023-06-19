package ru.cwe.common.listener.impl.listener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.cwe.common.listener.api.listener.PollingListener;
import ru.cwe.common.listener.api.buffer.ListenerMessageBuffer;
import ru.cwe.common.listener.api.factory.ListenerFactory;
import ru.cwe.common.listener.api.record.ListenerRecord;
import ru.cwe.common.message.api.message.Message;
import ru.cwe.common.test.fakers.Fakers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RestartableListenerTest {

	@SneakyThrows
	@Test
	void shouldCheckSubscriptionUnSubscription() {
		List<TestListenerRecord> expected = new ArrayList<>();
		for (int i = 0; i < TestPollingListener.POLL_CALLING_QUANTITY; i++) {
			expected.add(new TestListenerRecord(new UUID(0L, TestPollingListener.LEAST_UUID_BEGIN + i)));
		}

		TestBuffer buffer = new TestBuffer();
		TestPollingListener pollingListener = new TestPollingListener();
		RestartableListener listener = new RestartableListener(new TestListenerFactory(pollingListener), buffer);

		listener.subscribe();
		Thread.sleep(10);

		listener.unsubscribe();
		Thread.sleep(10);

		assertThat(buffer.getRecords()).isEqualTo(expected);
		assertThat(pollingListener.isSubscribeCalled()).isTrue();
		assertThat(pollingListener.isUnsubscribeCalled()).isTrue();
		assertThat(pollingListener.isCloseCalled()).isTrue();
	}

	private static class TestBuffer implements ListenerMessageBuffer {
		@Getter
		private final List<ListenerRecord> records = new ArrayList<>();

		@Override
		public boolean offer(ListenerRecord record) {
			this.records.add(record);
			return true;
		}
	}

	@RequiredArgsConstructor
	private static class TestListenerFactory implements ListenerFactory<PollingListener>{
		private final PollingListener pollingListener;

		@Override
		public PollingListener create() {
			return pollingListener;
		}
	}

	@RequiredArgsConstructor
	private static class TestPollingListener implements PollingListener {
		private static final long LEAST_UUID_BEGIN = 0L; // TODO: 19.06.2023 !!!
		private static final int POLL_CALLING_QUANTITY = Fakers.int_().between(5, 20);

		private static long leastUuid = LEAST_UUID_BEGIN;

		@Getter private boolean subscribeCalled;
		@Getter private boolean unsubscribeCalled;
		@Getter private boolean closeCalled;

		private int callCounter = 0;

		@Override
		public void subscribe() { this.subscribeCalled = true; }
		@Override
		public void unsubscribe() { this.unsubscribeCalled = true; }

		@SneakyThrows
		@Override
		public List<ListenerRecord> poll() {
			if (callCounter < POLL_CALLING_QUANTITY){
				callCounter++;
				return List.of(new TestListenerRecord(new UUID(0L, leastUuid++)));
			}
			return List.of();
		}
		@Override
		public void close() { this.closeCalled = true; }
	}

	@EqualsAndHashCode
	@RequiredArgsConstructor
	private static class TestListenerRecord implements ListenerRecord {
		private final UUID key;

		@Override
		public UUID key() {return this.key;}

		@Override
		public Message value() {return null;}
		@Override
		public <T> T get(String property, Class<T> type) {return null;}
	}
}
