package ru.cwe.common.listener.impl.record;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import ru.cwe.common.listener.api.record.ListenerRecord;
import ru.cwe.common.message.api.message.Message;
import ru.cwe.common.test.fakers.Fakers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class KafkaRecordConverterTest {

	@Test
	void shouldCheckConversion() {
		String expectedTopic = Fakers.str_().random();
		int expectedPartition = Fakers.int_().between(0, 10);
		long expectedOffset = Fakers.long_().between(0L, 10);
		UUID expectedKey = Fakers.uuid_().random();
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
