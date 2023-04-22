package ru.cwe.common.message.confirmation;

import ru.cwe.common.message.Message;

public interface ConfirmationMessage extends Message {
	ConfirmationResult getResult();
	String getPayloadMessageType();
}
