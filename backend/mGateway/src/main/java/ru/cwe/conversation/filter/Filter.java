package ru.cwe.conversation.filter;

import ru.cwe.conversation.message.Message;

public interface Filter {
	boolean filter(Message message);
}
