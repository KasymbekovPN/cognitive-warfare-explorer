package ru.cwe.common._temp;

import lombok.Getter;
import ru.cwe.common.message.api.message.Message;

import java.util.UUID;

@Getter
public class _TempKafkaTemplate {
	private String topic;
	private UUID key;
	private Message message;
	private boolean flushCalled;

	public _TempFuture send(String topic, UUID key, Message message) {
		this.topic = topic;
		this.key = key;
		this.message = message;

		return new _TempFuture();
	}

	public void flush(){
		this.flushCalled = true;
	}
}
