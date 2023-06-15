package ru.cwe.common.listener.impl.record;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import ru.cwe.common.listener.api.record.ListenerRecord;
import ru.cwe.common.listener.api.record.ListenerRecordUnsupportedGetting;
import ru.cwe.common.message.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@RequiredArgsConstructor
class KafkaListenerRecord implements ListenerRecord {
	private static final Map<String, Function<ConsumerRecord<UUID, Message>, Object>> GETTERS = new HashMap<>(){{
		put(Property.TOPIC.getValue(), KafkaListenerRecord::getTopic);
		put(Property.PARTITION.getValue(), KafkaListenerRecord::getPartition);
		put(Property.OFFSET.getValue(), KafkaListenerRecord::getOffset);
		put(Property.TIMESTAMP.getValue(), KafkaListenerRecord::getTimestamp);
		put(Property.TIMESTAMP_TYPE.getValue(), KafkaListenerRecord::getTimestampType);
	}};

	private final ConsumerRecord<UUID, Message> essence;

	@Override
	public UUID key() {
		return essence.key();
	}

	@Override
	public Message value() {
		return essence.value();
	}

	@Override
	public <T> T get(String property, Class<T> type) {
		if (GETTERS.containsKey(property)){
			return type.cast(GETTERS.get(property).apply(essence));
		}

		throw new ListenerRecordUnsupportedGetting(property);
	}

	private static Object getTopic(ConsumerRecord<UUID, Message> essence){
		return essence.topic();
	}

	private static Object getPartition(ConsumerRecord<UUID, Message> essence){
		return essence.partition();
	}

	private static Object getOffset(ConsumerRecord<UUID, Message> essence){
		return essence.offset();
	}

	private static Object getTimestamp(ConsumerRecord<UUID, Message> essence){
		return essence.timestamp();
	}

	private static Object getTimestampType(ConsumerRecord<UUID, Message> essence){
		return essence.timestampType();
	}

	@RequiredArgsConstructor
	@Getter
	public enum Property {
		TOPIC("topic"),
		PARTITION("partition"),
		OFFSET("offset"),
		TIMESTAMP("timestamp"),
		TIMESTAMP_TYPE("timestampType");

		private final String value;
	}
}
