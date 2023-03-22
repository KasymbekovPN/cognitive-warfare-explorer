package ru.cwe.conversation;

import ru.cwe.conversation.message.Message;

public interface Tube {
	void append(Message message);
}
