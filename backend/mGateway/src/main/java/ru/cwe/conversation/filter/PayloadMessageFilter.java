package ru.cwe.conversation.filter;

import ru.cwe.conversation.message.Message;
import ru.cwe.conversation.message.MessageType;

public final class PayloadMessageFilter implements Filter{
	@Override
	public boolean filter(Message message) {
		return message.getType().equals(MessageType.REQUEST) ||
			   message.getType().equals(MessageType.RESPONSE);
	}
}
