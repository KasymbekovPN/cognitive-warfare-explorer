package ru.cwe.bus.balancer;

import ru.cwe.conversation.message.payload.PayloadMessage;

public interface Balancer {
	void balance(PayloadMessage message);
}
