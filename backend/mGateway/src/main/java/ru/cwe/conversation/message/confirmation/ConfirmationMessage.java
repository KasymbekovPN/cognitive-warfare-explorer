package ru.cwe.conversation.message.confirmation;

import ru.cwe.conversation.message.Message;

// TODO: 22.04.2023 del
public interface ConfirmationMessage extends Message {
	ConfirmationResult getResult();
	String getPayloadMessageType();
}
