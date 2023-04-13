package ru.cwe.conversation.filter;

import ru.cwe.conversation.message.Message;
import ru.cwe.conversation.message.MessageType;

public final class ConfirmationMessageFilter implements Filter {
	@Override
	public boolean filter(final Message message) {
		return message.getType().equals(MessageType.CONFIRMATION);
	}
}
