package ru.cwe.conversation.tube;

import ru.cwe.conversation.message.payload.PayloadMessage;

public interface Tube {
	void send(PayloadMessage message);
	int size();
}
