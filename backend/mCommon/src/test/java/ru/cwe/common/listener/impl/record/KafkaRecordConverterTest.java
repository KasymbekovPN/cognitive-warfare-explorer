package ru.cwe.common.listener.impl.record;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import ru.cwe.common.listener.api.record.ListenerRecord;
import ru.cwe.common.message.api.message.Message;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class KafkaRecordConverterTest {

	@Test
	void shouldCheckConversion() {
		String expectedTopic = "topic";
		int expectedPartition = 0;
		int expectedOffset = 1;
		UUID expectedKey = UUID.randomUUID();
		TestMessage expectedValue = new TestMessage();
		ConsumerRecord<UUID, Message> consumerRecord = new ConsumerRecord<>(
			expectedTopic,
			expectedPartition,
			expectedOffset,
			expectedKey,
			expectedValue
		);

		ListenerRecord result = new KafkaRecordConverter().apply(consumerRecord);
		assertThat(result.key()).isEqualTo(expectedKey);
		assertThat(result.value()).isEqualTo(expectedValue);
		assertThat(result.get("topic", String.class)).isEqualTo(expectedTopic);
		assertThat(result.get("partition", Integer.class)).isEqualTo(expectedPartition);
		assertThat(result.get("offset", Long.class)).isEqualTo(expectedOffset);
	}

	private static class TestMessage implements Message{}
}
