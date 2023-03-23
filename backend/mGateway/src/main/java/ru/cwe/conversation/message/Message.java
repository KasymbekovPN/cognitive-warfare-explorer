package ru.cwe.conversation.message;

import java.util.UUID;

public interface Message {
	UUID getUuid();
	MessageType type();
}
