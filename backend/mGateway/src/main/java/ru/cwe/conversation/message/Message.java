package ru.cwe.conversation.message;

import java.util.UUID;

public interface Message {
	int getVersion();
	int getPriority();
	MessageType getType();
	UUID getUuid();
}
