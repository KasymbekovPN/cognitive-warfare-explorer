package ru.cwe.conversation.gateway.out;

import ru.cwe.conversation.message.payload.PayloadMessage;

public interface OutGateway {
	void send(PayloadMessage message);
	void shutdown();
}
