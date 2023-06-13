package ru.cwe.common.buffer;

import ru.cwe.common.message.Message;

public interface KafkaListenerMessageBuffer {
	boolean offer(Message message);
}
