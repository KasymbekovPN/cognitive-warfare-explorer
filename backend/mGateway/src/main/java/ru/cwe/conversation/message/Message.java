package ru.cwe.conversation.message;

import java.util.UUID;

public interface Message {
	int getVersion();
	MessageType getType();
	UUID getUuid();
}
