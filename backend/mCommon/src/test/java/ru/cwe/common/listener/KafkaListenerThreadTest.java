package ru.cwe.common.listener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.cwe.common.buffer.ListenerMessageBuffer;
import ru.cwe.common.message.Message;
import ru.cwe.common.record.ListenerRecord;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class KafkaListenerThreadTest {

	@RequiredArgsConstructor
	private static class TestListenerRecord implements ListenerRecord{
		private final UUID key;

		@Override
		public UUID key() {
			return key;
		}

		@Override
		public Message value() {return null;}
		@Override
		public <T> T get(String property, Class<T> type) {return null;}
	}

	@RequiredArgsConstructor
	@Getter
	private static class TestListener implements Listener {
		private final List<ListenerRecord> records;

		private boolean subscribeCalled;
		private boolean unsubscribeCalled;
		private boolean closeCalled;

		@Override
		public void subscribe() {
			this.subscribeCalled = true;
		}

		@Override
		public void unsubscribe() {
			this.unsubscribeCalled = true;
		}

		@Override
		public List<ListenerRecord> poll() {
			return records;
		}

		@Override
		public void close() throws IOException {
			this.closeCalled = true;
		}
	}

	private static class TestListenerMessageBuffer implements ListenerMessageBuffer{
		@Override
		public boolean offer(ListenerRecord record) {
			return false;
		}
	}
}
