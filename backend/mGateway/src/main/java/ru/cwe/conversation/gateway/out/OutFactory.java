package ru.cwe.conversation.gateway.out;

import ru.cwe.conversation.container.MessageContainer;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;

public interface OutFactory {
	OutGateway create(MessageContainer<ConfirmationMessage> container,
					  String host,
					  int port);
}
