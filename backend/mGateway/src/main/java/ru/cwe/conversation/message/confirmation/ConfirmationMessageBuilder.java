package ru.cwe.conversation.message.confirmation;

import ru.cwe.conversation.exception.AbsentFieldRuntimeExceptionBuilder;
import ru.cwe.conversation.exception.AbsentFieldRuntimeExceptionBuilderImpl;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.payload.PayloadMessage;

import java.util.*;

public final class ConfirmationMessageBuilder {
	private final AbsentFieldRuntimeExceptionBuilder exceptionBuilder
		= new AbsentFieldRuntimeExceptionBuilderImpl(ConfirmationMessageBuilderException::new);

	private UUID uuid;
	private ConfirmationResult result;
	private String payloadMessageType = "";

	public static ConfirmationMessageBuilder builder(){
		return new ConfirmationMessageBuilder();
	}

	private ConfirmationMessageBuilder() {
	}

	ConfirmationMessageBuilder uuid(UUID uuid){
		this.uuid = uuid;
		return this;
	}

	ConfirmationMessageBuilder result(ConfirmationResult result){
		this.result = result;
		return this;
	}

	ConfirmationMessageBuilder payloadMessageType(String payloadMessageType){
		this.payloadMessageType = payloadMessageType;
		return this;
	}

	ConfirmationMessageBuilder fromPayloadMessage(PayloadMessage payloadMessage){
		this.uuid = payloadMessage.getUuid();
		this.result = payloadMessage.getType().equals(MessageType.REQUEST)
			? ConfirmationResult.REQUEST
			: ConfirmationResult.RESPONSE;
		return this;
	}

	ConfirmationMessageBuilder error(Object invalidMessage){
		this.uuid = new UUID(0, 0);
		this.result = ConfirmationResult.INVALID;
		this.payloadMessageType = invalidMessage.getClass().getSimpleName();
		return this;
	}

	ConfirmationMessage build(){
		Optional<RuntimeException> maybeException = exceptionBuilder
			.checkField("uuid", uuid)
			.checkField("result", result)
			.build();
		if (maybeException.isPresent()){
			throw maybeException.get();
		}

		return new ConfirmationMessageImpl(uuid, result, payloadMessageType);
	}
}
