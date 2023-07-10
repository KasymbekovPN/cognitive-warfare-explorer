package ru.cwe.common.record.impl;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import ru.cwe.common.message.api.message.Message;
import ru.cwe.common.record.api.Record;

import java.util.UUID;

@RequiredArgsConstructor
@EqualsAndHashCode
public class KafkaSendRecord implements Record {
	private final String topic;
	private final UUID uuid;
	private final Message message;

	@Override
	public UUID key() {
		return uuid;
	}

	@Override
	public Message value() {
		return message;
	}

	@Override
	public <T> T get(String property, Class<T> type) {
		if (property.equals("topic")){
			return type.cast(topic);
		}
		throw new RecordUnsupportedGetting(String.format("%s does not support getting", getClass().getSimpleName()));
	}
}
