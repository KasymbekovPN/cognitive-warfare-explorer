package ru.cwe.conversation.message.confirmation;

import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.payload.PayloadMessage;

import java.util.UUID;

public final class ConfirmationMessageBuilder {
	private UUID uuid;
	private MessageType type;
	private ConfirmationResult result;
	private String payloadMessageType;

	ConfirmationMessageBuilder uuid(UUID uuid){
		this.uuid = uuid;
		return this;
	}

	ConfirmationMessageBuilder result(ConfirmationResult result){
		return this;
	}

	ConfirmationMessageBuilder payloadMessageType(){
		return this;
	}

	ConfirmationMessageBuilder fromPayloadMessage(PayloadMessage payloadMessage){
		return this;
	}

	ConfirmationMessageBuilder error(Object invalidMessage){
		return this;
	}

	ConfirmationMessage build(){
		return null;
	}
}
