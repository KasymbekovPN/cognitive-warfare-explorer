package ru.cwe.conversation.tube;

import ru.cwe.conversation.message.payload.PayloadMessage;

// TODO: 18.04.2023 move to own package
// TODO: 18.04.2023 ???
public interface DatumCreator {
	DatumCreator message(PayloadMessage message);
	DatumCreator host(String host);
	DatumCreator port(int port);
	TubeOld put();
}
