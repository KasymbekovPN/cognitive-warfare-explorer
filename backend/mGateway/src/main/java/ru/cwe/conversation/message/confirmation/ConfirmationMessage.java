package ru.cwe.conversation.message.confirmation;

import ru.cwe.conversation.message.Message;

public interface ConfirmationMessage extends Message {
	ConfirmationResult getResult();
	String getPayloadMessageType();
}
