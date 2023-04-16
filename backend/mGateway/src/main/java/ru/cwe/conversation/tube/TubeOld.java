package ru.cwe.conversation.tube;

import ru.cwe.conversation.message.payload.PayloadMessage;

public interface TubeOld {
	void send(PayloadMessage message);
	int size();
	// TODO: 16.04.2023 !!!
	// tube.creator().message(m).host(h).port(p).put();
}
