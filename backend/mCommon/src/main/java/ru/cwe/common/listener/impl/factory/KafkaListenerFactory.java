package ru.cwe.common.listener.impl.factory;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import ru.cwe.common.listener.api.factory.ListenerFactory;
import ru.cwe.common.listener.api.listener.PollingListener;
import ru.cwe.common.record.api.Record;
import ru.cwe.common.listener.impl.listener.KafkaListener;
import ru.cwe.common.message.api.message.Message;

import java.time.Duration;
import java.util.UUID;
import java.util.function.Function;

@RequiredArgsConstructor
public class KafkaListenerFactory implements ListenerFactory<PollingListener> {
	private final String topic;
	private final Duration duration;
	private final Consumer<UUID, Message> consumer;
	private final Function<ConsumerRecord<UUID, Message>, Record> converter;

	@Override
	public PollingListener create() {
		return new KafkaListener(topic, duration, consumer, converter);
	}
}
