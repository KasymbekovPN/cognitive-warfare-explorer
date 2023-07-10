package ru.cwe.common.action.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;
import ru.cwe.common._temp._TempKafkaTemplate;
import ru.cwe.common.message.api.message.Message;
import ru.cwe.common.record.impl.KafkaSendRecord;
import ru.cwe.common.test.fakers.Fakers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

class KafkaSendMessageActionTest {


//	private final String kafkaServer = "localhost:9092";
//
//	@Bean
//	public Map<String, Object> producerConfigs(){
//		return new HashMap<>(){{
//			put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
//			put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
//			put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//		}};
//	}

	@Test
	void shouldCheckExecution(){

		SuccessCallback<SendResult<UUID, Message>> expectedSuccessCallback = (SendResult<UUID, Message> r) -> {};
		FailureCallback expectedFailureCallback = (Throwable thr) -> {};

		String expectedTopic = Fakers.str_().random();
		UUID expectedKey = Fakers.uuid_().random();
		Message expectedMessage = new TestMessage(Fakers.int_().random());
		KafkaSendRecord record = new KafkaSendRecord(
			expectedTopic,
			expectedKey,
			expectedMessage
		);

		HashMap<String, Object> properties = new HashMap<>(){{
			put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		}};
		TestKafkaTemplate template = new TestKafkaTemplate(new DefaultKafkaProducerFactory<>(properties));

		KafkaSendMessageAction action = new KafkaSendMessageAction(template, expectedSuccessCallback, expectedFailureCallback);
		action.execute(record);

		assertThat(template.getTopic()).isEqualTo(expectedTopic);
		assertThat(template.getKey()).isEqualTo(expectedKey);
		assertThat(template.getData()).isEqualTo(expectedMessage);
		assertThat(template.getFuture().getSuccessCallback()).isEqualTo(expectedSuccessCallback);
		assertThat(template.getFuture().getFailureCallback()).isEqualTo(expectedFailureCallback);
	}

	@RequiredArgsConstructor
	@EqualsAndHashCode
	private static class TestMessage implements Message {
		private final int value;
	}

	@Getter
	private static class TestListenableFuture implements ListenableFuture<SendResult<UUID, Message>>{
		private SuccessCallback<? super SendResult<UUID, Message>> successCallback;
		private FailureCallback failureCallback;

		@Override
		public void addCallback(SuccessCallback<? super SendResult<UUID, Message>> successCallback, FailureCallback failureCallback) {
			this.successCallback = successCallback;
			this.failureCallback = failureCallback;
		}

		@Override
		public void addCallback(ListenableFutureCallback<? super SendResult<UUID, Message>> callback) {}
		@Override
		public boolean cancel(boolean mayInterruptIfRunning) { return false; }
		@Override
		public boolean isCancelled() { return false; }
		@Override
		public boolean isDone() { return false; }
		@Override
		public SendResult<UUID, Message> get() throws InterruptedException, ExecutionException { return null; }
		@Override
		public SendResult<UUID, Message> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException { return null; }
	}

	@Getter
	private static class TestKafkaTemplate extends KafkaTemplate<UUID, Message>{
		private String topic;
		private UUID key;
		private Message data;
		private boolean flushCalled;
		private TestListenableFuture future;

		public TestKafkaTemplate(ProducerFactory<UUID, Message> producerFactory) {
			super(producerFactory);
		}

		@Override
		public ListenableFuture<SendResult<UUID, Message>> send(String topic, UUID key, @Nullable Message data) {
			this.topic = topic;
			this.key = key;
			this.data = data;
			this.future = new TestListenableFuture();
			return future;
		}

		@Override
		public void flush() {
			this.flushCalled = true;
		}
	}
}
