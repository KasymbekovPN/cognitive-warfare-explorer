package ru.cwe.conversation.tube;

import ru.cwe.conversation.message.payload.PayloadMessage;

public interface DatumCreator {
	DatumCreator message(PayloadMessage message);
	DatumCreator host(String host);
	DatumCreator port(int port);
	TubeOld put();
}
