package ru.cwe.conversation.tube;

import ru.cwe.conversation.message.payload.PayloadMessage;

public interface TubeDatum {
	PayloadMessage getMessage();
	String getHost();
	int getPort();
}
