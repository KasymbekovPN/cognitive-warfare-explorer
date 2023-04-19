package ru.cwe.conversation.tube;

import ru.cwe.conversation.message.payload.PayloadMessage;

// TODO: 18.04.2023 move to own package
public interface TubeDatum {
	PayloadMessage getMessage();
	String getHost();
	int getPort();
}
