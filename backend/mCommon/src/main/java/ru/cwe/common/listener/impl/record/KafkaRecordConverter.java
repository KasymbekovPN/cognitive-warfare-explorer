package ru.cwe.common.listener.impl.record;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import ru.cwe.common.listener.api.record.ListenerRecord;
import ru.cwe.common.message.Message;

import java.util.UUID;
import java.util.function.Function;

public final class KafkaRecordConverter implements Function<ConsumerRecord<UUID, Message>, ListenerRecord> {
	@Override
	public ListenerRecord apply(ConsumerRecord<UUID, Message> record) {
		return new KafkaListenerRecord(record);
	}
}
