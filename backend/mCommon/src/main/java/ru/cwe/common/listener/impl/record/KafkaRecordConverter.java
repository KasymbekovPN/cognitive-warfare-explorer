package ru.cwe.common.listener.impl.record;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import ru.cwe.common.record.api.Record;
import ru.cwe.common.message.api.message.Message;

import java.util.UUID;
import java.util.function.Function;

public final class KafkaRecordConverter implements Function<ConsumerRecord<UUID, Message>, Record> {
	@Override
	public Record apply(ConsumerRecord<UUID, Message> record) {
		return new KafkaListenerRecord(record);
	}
}
