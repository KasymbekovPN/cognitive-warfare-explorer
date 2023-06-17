package ru.cwe.common.listener.impl.listener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.cwe.common.listener.api.record.ListenerRecord;
import ru.cwe.common.listener.impl.record.KafkaRecordConverter;
import ru.cwe.common.message.api.message.Message;

import java.time.Duration;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class KafkaListenerTest {
	private static final String TOPIC = "topic";
	private static final Duration DURATION = Duration.ofMillis(100);
	private static final int PARTITION = 1;
	private static final long OFFSET = 2L;
	private static final UUID KEY = UUID.randomUUID();
	private static final Message VALUE = new TestMessage(1);

	private static ConsumerRecords<UUID, Message> records;
	private static Map<String, Object> properties;

	@BeforeAll
	static void beforeAll() {
		TopicPartition partition = new TopicPartition(TOPIC, PARTITION);
		List<ConsumerRecord<UUID, Message>> recordList = List.of(
			new ConsumerRecord<>(TOPIC, PARTITION, OFFSET, KEY, VALUE)
		);
		records = new ConsumerRecords<>(new HashMap<>() {{
			put(partition, recordList);
		}});

		properties = new HashMap<String, Object>(){{
			put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
			put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
			put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
			put(ConsumerConfig.GROUP_ID_CONFIG, "g1");
		}};

	}

	@Test
	void shouldCheckSubscription() {
		TestConsumer consumer = new TestConsumer(properties, records);
		KafkaListener listener = new KafkaListener(TOPIC, DURATION, consumer, new KafkaRecordConverter());

		listener.subscribe();
		assertThat(consumer.getTopics()).isEqualTo(List.of(TOPIC));
	}

	@Test
	void shouldCheckUnsubscription() {
		TestConsumer consumer = new TestConsumer(properties, records);
		KafkaListener listener = new KafkaListener(TOPIC, DURATION, consumer, new KafkaRecordConverter());

		listener.unsubscribe();
		assertThat(consumer.isUnsubscribeCalled()).isTrue();
	}

	@Test
	void shouldCheckPolling() {
		TestConsumer consumer = new TestConsumer(properties, records);
		KafkaListener listener = new KafkaListener(TOPIC, DURATION, consumer, new KafkaRecordConverter());

		List<ListenerRecord> result = listener.poll();
		assertThat(consumer.getDuration()).isEqualTo(DURATION);
		assertThat(result.size()).isEqualTo(1);

		ListenerRecord record = result.get(0);
		assertThat(record.key()).isEqualTo(KEY);
		assertThat(record.value()).isEqualTo(VALUE);
		assertThat(record.get("topic", String.class)).isEqualTo(TOPIC);
		assertThat(record.get("partition", Integer.class)).isEqualTo(PARTITION);
		assertThat(record.get("offset", Long.class)).isEqualTo(OFFSET);
	}

	@Test
	void shouldCheckClose() {
		TestConsumer consumer = new TestConsumer(properties, records);
		KafkaListener listener = new KafkaListener(TOPIC, DURATION, consumer, new KafkaRecordConverter());

		listener.close();
		assertThat(consumer.isCloseCalled()).isTrue();
	}

	@RequiredArgsConstructor
	@Getter
	private static class TestMessage implements Message {
		private final int value;
	}

	private static class TestConsumer extends KafkaConsumer<UUID, Message> {
		private final ConsumerRecords<UUID, Message> records;

		@Getter private boolean unsubscribeCalled;
		@Getter private Duration duration;
		@Getter private boolean closeCalled;
		@Getter private Collection<String> topics;

		public TestConsumer(Map<String, Object> configs, ConsumerRecords<UUID, Message> records) {
			super(configs);
			this.records = records;
		}

		@Override
		public void subscribe(Collection<String> topics) {
			this.topics = topics;
		}

		@Override
		public void unsubscribe() {
			this.unsubscribeCalled = true;
		}

		@Override
		public ConsumerRecords<UUID, Message> poll(Duration timeout) {
			this.duration = timeout;
			return records;
		}

		@Override
		public void close() {
			this.closeCalled = true;
		}
	}
}
