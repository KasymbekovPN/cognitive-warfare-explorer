package ru.cwe.conversation.filter;

import ru.cwe.conversation.message.Message;
import ru.cwe.conversation.message.MessageType;

public final class RequestMessageFilter implements Filter<Message> {
	@Override
	public boolean filter(final Message message) {
		return message.getType().equals(MessageType.REQUEST);
	}
}
