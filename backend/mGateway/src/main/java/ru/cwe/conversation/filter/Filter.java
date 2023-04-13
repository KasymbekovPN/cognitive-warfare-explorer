package ru.cwe.conversation.filter;

import ru.cwe.conversation.message.Message;

public interface Filter<M> {
	boolean filter(M message);
}
