package ru.cwe.common.listener.impl.factory;

import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.cwe.common.listener.api.listener.PollingListener;
import ru.cwe.common.listener.api.record.ListenerRecord;
import ru.cwe.common.message.api.message.Message;
import ru.cwe.common.reflection.Reflections;
import ru.cwe.common.test.fakers.Fakers;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class KafkaListenerFactoryTest {
	private static final String EXPECTED_TOPIC = Fakers.str_().random();
	private static final Duration EXPECTED_DURATION = Duration.ofSeconds(Fakers.int_().between(100, 200));
	private static final Consumer<UUID, Message> EXPECTED_CONSUMER = new TestConsumer();
	private static final Function<ConsumerRecord<UUID, Message>, ListenerRecord> EXPECTED_CONVERTER = new TestConverter();

	private static KafkaListenerFactory factory;

	@BeforeAll
	static void beforeAll(){
		factory = new KafkaListenerFactory(EXPECTED_TOPIC, EXPECTED_DURATION, EXPECTED_CONSUMER, EXPECTED_CONVERTER);
	}

	@Test
	void shouldCheckCreation_notNull(){
		PollingListener listener = factory.create();
		assertThat(listener).isNotNull();
	}

	@SneakyThrows
	@Test
	void shouldCheckCreation_topic(){
		PollingListener listener = factory.create();
		String topic = Reflections.get(listener, "topic", String.class);

		assertThat(topic).isEqualTo(EXPECTED_TOPIC);
	}

	@SneakyThrows
	@Test
	void shouldCheckCreation_duration(){
		PollingListener listener = factory.create();
		Duration duration = Reflections.get(listener, "duration", Duration.class);

		assertThat(duration).isEqualTo(EXPECTED_DURATION);
	}

	@SneakyThrows
	@Test
	void shouldCheckCreation_consumer(){
		PollingListener listener = factory.create();
		Object consumer = Reflections.get(listener, "consumer");

		assertThat(consumer).isEqualTo(EXPECTED_CONSUMER);
	}

	@SneakyThrows
	@Test
	void shouldCheckCreation_converter(){
		PollingListener listener = factory.create();
		Object converter = Reflections.get(listener, "converter");

		assertThat(converter).isEqualTo(EXPECTED_CONVERTER);
	}

	private static class TestConverter implements Function<ConsumerRecord<UUID, Message>, ListenerRecord>{
		@Override
		public ListenerRecord apply(ConsumerRecord<UUID, Message> uuidMessageConsumerRecord) {
			return null;
		}
	}

	private static class TestConsumer implements Consumer<UUID, Message>{
		@Override
		public Set<TopicPartition> assignment() {return null;}
		@Override
		public Set<String> subscription() {return null;}
		@Override
		public void subscribe(Collection<String> topics) {}
		@Override
		public void subscribe(Collection<String> topics, ConsumerRebalanceListener callback) {}
		@Override
		public void assign(Collection<TopicPartition> partitions) {}
		@Override
		public void subscribe(Pattern pattern, ConsumerRebalanceListener callback) {}
		@Override
		public void subscribe(Pattern pattern) {}
		@Override
		public void unsubscribe() {}
		@Override
		public ConsumerRecords<UUID, Message> poll(long timeout) {return null;}
		@Override
		public ConsumerRecords<UUID, Message> poll(Duration timeout) {return null;}
		@Override
		public void commitSync() {}
		@Override
		public void commitSync(Duration timeout) {}
		@Override
		public void commitSync(Map<TopicPartition, OffsetAndMetadata> offsets) {}
		@Override
		public void commitSync(Map<TopicPartition, OffsetAndMetadata> offsets, Duration timeout) {}
		@Override
		public void commitAsync() {}
		@Override
		public void commitAsync(OffsetCommitCallback callback) {}
		@Override
		public void commitAsync(Map<TopicPartition, OffsetAndMetadata> offsets, OffsetCommitCallback callback) {}
		@Override
		public void seek(TopicPartition partition, long offset) {}
		@Override
		public void seek(TopicPartition partition, OffsetAndMetadata offsetAndMetadata) {}
		@Override
		public void seekToBeginning(Collection<TopicPartition> partitions) {}
		@Override
		public void seekToEnd(Collection<TopicPartition> partitions) {}
		@Override
		public long position(TopicPartition partition) {return 0;}
		@Override
		public long position(TopicPartition partition, Duration timeout) {return 0;}
		@Override
		public OffsetAndMetadata committed(TopicPartition partition) {return null;}
		@Override
		public OffsetAndMetadata committed(TopicPartition partition, Duration timeout) {return null;}
		@Override
		public Map<TopicPartition, OffsetAndMetadata> committed(Set<TopicPartition> partitions) {return null;}
		@Override
		public Map<TopicPartition, OffsetAndMetadata> committed(Set<TopicPartition> partitions, Duration timeout) {return null;}
		@Override
		public Map<MetricName, ? extends Metric> metrics() {return null;}
		@Override
		public List<PartitionInfo> partitionsFor(String topic) {return null;}
		@Override
		public List<PartitionInfo> partitionsFor(String topic, Duration timeout) {return null;}
		@Override
		public Map<String, List<PartitionInfo>> listTopics() {return null;}
		@Override
		public Map<String, List<PartitionInfo>> listTopics(Duration timeout) {return null;}
		@Override
		public Set<TopicPartition> paused() {return null;}
		@Override
		public void pause(Collection<TopicPartition> partitions) {}
		@Override
		public void resume(Collection<TopicPartition> partitions) {}
		@Override
		public Map<TopicPartition, OffsetAndTimestamp> offsetsForTimes(Map<TopicPartition, Long> timestampsToSearch) {return null;}
		@Override
		public Map<TopicPartition, OffsetAndTimestamp> offsetsForTimes(Map<TopicPartition, Long> timestampsToSearch, Duration timeout) {return null;}
		@Override
		public Map<TopicPartition, Long> beginningOffsets(Collection<TopicPartition> partitions) {return null;}
		@Override
		public Map<TopicPartition, Long> beginningOffsets(Collection<TopicPartition> partitions, Duration timeout) {return null;}
		@Override
		public Map<TopicPartition, Long> endOffsets(Collection<TopicPartition> partitions) {return null;}
		@Override
		public Map<TopicPartition, Long> endOffsets(Collection<TopicPartition> partitions, Duration timeout) {return null;}
		@Override
		public ConsumerGroupMetadata groupMetadata() {return null;}
		@Override
		public void enforceRebalance() {}
		@Override
		public void close() {}
		@Override
		public void close(long timeout, TimeUnit unit) {}
		@Override
		public void close(Duration timeout) {}
		@Override
		public void wakeup() {}
	}
}
