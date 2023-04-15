package ru.cwe.bus.balancer;

import ru.cwe.conversation.message.payload.PayloadMessage;

// TODO: 15.04.2023 add by-tube-size balancer
public interface Balancer {
	void balance(PayloadMessage message);
}
