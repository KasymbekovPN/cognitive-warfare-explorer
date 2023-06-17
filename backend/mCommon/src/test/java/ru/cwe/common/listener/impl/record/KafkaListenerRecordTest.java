package ru.cwe.common.listener.impl.record;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.record.TimestampType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.cwe.common.listener.api.record.ListenerRecordUnsupportedGetting;
import ru.cwe.common.message.api.message.Message;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class KafkaListenerRecordTest {
	private static final String TOPIC = "test";
	private static final int PARTITION = 0;
	private static final long OFFSET = 1;
	private static final long TIMESTAMP = 100;
	private static final TimestampType TIMESTAMP_TYPE = TimestampType.CREATE_TIME;
	private static final UUID KEY = UUID.randomUUID();
	private static final Message VALUE = new TestMessage(1);

	private static ConsumerRecord<UUID, Message> record;

	@BeforeAll
	static void beforeAll() {
		record = new ConsumerRecord<>(
			TOPIC,
			PARTITION,
			OFFSET,
			TIMESTAMP,
			TIMESTAMP_TYPE,
			0,
			0,
			0,
			KEY,
			VALUE
		);
	}

	@Test
	void shouldCheckKeyGetting() {
		UUID key = new KafkaListenerRecord(record).key();
		assertThat(key).isEqualTo(KEY);
	}

	@Test
	void shouldCheckValueGetting() {
		Message value = new KafkaListenerRecord(record).value();
		assertThat(value).isEqualTo(VALUE);
	}

	@Test
	void shouldCheckTopicGetting() {
		String topic = new KafkaListenerRecord(record).get(KafkaListenerRecord.Property.TOPIC.getValue(), String.class);
		assertThat(topic).isEqualTo(TOPIC);
	}

	@Test
	void shouldCheckPartitionGetting() {
		Integer partition = new KafkaListenerRecord(record).get(KafkaListenerRecord.Property.PARTITION.getValue(), Integer.class);
		assertThat(partition).isEqualTo(PARTITION);
	}

	@Test
	void shouldCheckOffsetGetting() {
		Long offset = new KafkaListenerRecord(record).get(KafkaListenerRecord.Property.OFFSET.getValue(), Long.class);
		assertThat(offset).isEqualTo(OFFSET);
	}

	@Test
	void shouldCheckTimestampGetting() {
		Long timestamp = new KafkaListenerRecord(record).get(KafkaListenerRecord.Property.TIMESTAMP.getValue(), Long.class);
		assertThat(timestamp).isEqualTo(TIMESTAMP);
	}

	@Test
	void shouldCheckTimestampTypeGetting() {
		TimestampType timestampType = new KafkaListenerRecord(record).get(KafkaListenerRecord.Property.TIMESTAMP_TYPE.getValue(), TimestampType.class);
		assertThat(timestampType).isEqualTo(TIMESTAMP_TYPE);
	}

	@Test
	void shouldCheckUnsupportedGetting() {
		Throwable throwable = catchThrowable(() -> {
			new KafkaListenerRecord(record).get("", Integer.class);
		});
		assertThat(throwable).isInstanceOf(ListenerRecordUnsupportedGetting.class);
	}

	@RequiredArgsConstructor
	@EqualsAndHashCode
	private static class TestMessage implements Message {
		private final int x;
	}
}
