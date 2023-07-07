package ru.cwe.common.listener.impl.listener;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import ru.cwe.common.listener.api.listener.PollingListener;
import ru.cwe.common.listener.api.record.ListenerRecord;
import ru.cwe.common.message.api.message.Message;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@RequiredArgsConstructor
public final class KafkaListener implements PollingListener {
	private final String topic;
	private final Duration duration;
	private final Consumer<UUID, Message> consumer;
	private final Function<ConsumerRecord<UUID, Message>, ListenerRecord> converter;

	@Override
	public void subscribe() {
		this.consumer.subscribe(List.of(topic));
	}

	@Override
	public void unsubscribe() {
		this.consumer.unsubscribe();
	}

	@Override
	public List<ListenerRecord> poll() {
		ConsumerRecords<UUID, Message> records = consumer.poll(this.duration);
		ArrayList<ListenerRecord> result = new ArrayList<>();
		records.forEach(record -> { result.add(converter.apply(record)); });
		return result;
	}

	@Override
	public void close() {
		this.consumer.close();
	}
}
