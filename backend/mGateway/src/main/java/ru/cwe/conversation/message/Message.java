package ru.cwe.conversation.message;

import java.util.UUID;

// TODO: 22.04.2023 del
public interface Message {
	int getVersion();
	int getPriority();
	MessageType getType();
	UUID getUuid();
}
