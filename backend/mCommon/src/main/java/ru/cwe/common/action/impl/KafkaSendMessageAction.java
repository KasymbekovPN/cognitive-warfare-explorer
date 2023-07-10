package ru.cwe.common.action.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import ru.cwe.common.action.api.Action;
import ru.cwe.common.message.api.message.Message;
import ru.cwe.common.record.impl.KafkaSendRecord;

import java.util.UUID;

@Slf4j
public class KafkaSendMessageAction implements Action<KafkaSendRecord> {
	private final KafkaTemplate<UUID, Message> kafkaTemplate;
	private final SuccessCallback<? super SendResult<UUID, Message>> successCallback;
	private final FailureCallback failureCallback;

	public KafkaSendMessageAction(final KafkaTemplate<UUID, Message> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
		this.successCallback = this::callDefaultSuccessCallback;
		this.failureCallback = this::callDefaultFailureCallback;
	}

	public KafkaSendMessageAction(final KafkaTemplate<UUID, Message> kafkaTemplate,
								  final SuccessCallback<? super SendResult<UUID, Message>> successCallback,
								  final FailureCallback failureCallback) {
		this.kafkaTemplate = kafkaTemplate;
		this.successCallback = successCallback;
		this.failureCallback = failureCallback;
	}

	@Override
	public void execute(final KafkaSendRecord record) {
		ListenableFuture<SendResult<UUID, Message>> future
			= kafkaTemplate.send(record.get("topic", String.class), record.key(), record.value());
		future.addCallback(successCallback, failureCallback);
		kafkaTemplate.flush();
	}

	private void callDefaultSuccessCallback(final Object value) {
		log.info("Success {}", value);
	}

	private void callDefaultFailureCallback(final Object value) {
		log.info("Fail {}", value);
	}
}
