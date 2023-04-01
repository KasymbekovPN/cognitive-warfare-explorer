package ru.cwe.conversation.message.confirmation;

import ru.cwe.conversation.exception.AbsentFieldRuntimeExceptionBuilderImpl;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.Priorities;
import ru.cwe.conversation.message.Versions;
import ru.cwe.conversation.message.payload.PayloadMessage;

import java.util.*;
import java.util.function.Function;

public final class ConfirmationMessageBuilder {
	private final ExceptionBuilder exceptionBuilder
		= new ExceptionBuilder(ConfirmationMessageBuilderException::new);

	private Integer version;
	private Integer priority = Priorities.MIN;
	private UUID uuid;
	private ConfirmationResult result;
	private String payloadMessageType = "";

	public static ConfirmationMessageBuilder builder(){
		return new ConfirmationMessageBuilder();
	}

	private ConfirmationMessageBuilder() {
	}

	public ConfirmationMessageBuilder version(int version){
		this.version = version;
		return this;
	}

	public ConfirmationMessageBuilder priority(int priority){
		this.priority = Priorities.adjust(priority);
		return this;
	}

	public ConfirmationMessageBuilder uuid(UUID uuid){
		this.uuid = uuid;
		return this;
	}

	public ConfirmationMessageBuilder result(ConfirmationResult result){
		this.result = result;
		return this;
	}

	public ConfirmationMessageBuilder payloadMessageType(String payloadMessageType){
		this.payloadMessageType = payloadMessageType;
		return this;
	}

	public ConfirmationMessageBuilder fromPayloadMessage(PayloadMessage payloadMessage){
		this.uuid = payloadMessage.getUuid();
		this.result = payloadMessage.getType().equals(MessageType.REQUEST)
			? ConfirmationResult.REQUEST
			: ConfirmationResult.RESPONSE;
		this.version = payloadMessage.getVersion();
		this.priority = Priorities.adjust(payloadMessage.getPriority());
		return this;
	}

	public ConfirmationMessageBuilder error(Object invalidMessage){
		this.version = 0;
		this.priority = Priorities.MAX;
		this.uuid = new UUID(0, 0);
		this.result = ConfirmationResult.INVALID;
		this.payloadMessageType = invalidMessage.getClass().getSimpleName();
		return this;
	}

	public ConfirmationMessageBuilder reset(){
		this.version = null;
		this.priority = Priorities.MIN;
		this.uuid = null;
		this.result = null;
		this.payloadMessageType = "";
		return this;
	}

	public ConfirmationMessage build(){
		exceptionBuilder
			.checkField("version", version)
			.checkField("uuid", uuid)
			.checkField("result", result);
		Optional<RuntimeException> maybeException = exceptionBuilder
			.checkVersion(version)
			.build();
		if (maybeException.isPresent()){
			throw maybeException.get();
		}

		return new ConfirmationMessageImpl(version, priority, uuid, result, payloadMessageType);
	}

	private static class ExceptionBuilder extends AbsentFieldRuntimeExceptionBuilderImpl{
		public ExceptionBuilder(Function<String, RuntimeException> creator) {
			super(creator);
		}

		public ExceptionBuilder checkVersion(Integer version){
			if (version != null && !Versions.check(version)){
				appendPartDelimiterAndGet().append("invalid version");
			}
			return this;
		}
	}
}
