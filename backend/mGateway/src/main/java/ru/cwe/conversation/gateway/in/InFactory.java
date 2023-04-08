package ru.cwe.conversation.gateway.in;

import ru.cwe.conversation.container.MessageContainer;
import ru.cwe.conversation.message.payload.PayloadMessage;

public interface InFactory {
	InGateway create(MessageContainer<PayloadMessage> requestMessageContainer,
					 MessageContainer<PayloadMessage> responseMessageContainer,
					 int port);
}
