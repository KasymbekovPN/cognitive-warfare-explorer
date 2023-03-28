package ru.cwe.conversation.message.payload;

import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.exception.AbsentFieldRuntimeExceptionBuilderImpl;
import ru.cwe.conversation.message.MessageType;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public final class PayloadMessageBuilder {
	private final ExceptionBuilder exceptionBuilder
		= new ExceptionBuilder(PayloadMessageBuilderException::new);

	private UUID uuid;
	private MessageType type;
	private String contentType;
	private String content;
	private Address from;
	private Address to;

	public static PayloadMessageBuilder builder(){
		return new PayloadMessageBuilder();
	}

	private PayloadMessageBuilder() {
	}

	public PayloadMessageBuilder uuid(UUID uuid){
		this.uuid = uuid;
		return this;
	}

	public PayloadMessageBuilder type(MessageType type){
		this.type = type;
		return this;
	}

	public PayloadMessageBuilder contentType(String contentType){
		this.contentType = contentType;
		return this;
	}

	public PayloadMessageBuilder content(String content){
		this.content = content;
		return this;
	}

	public PayloadMessageBuilder from(Address from){
		this.from = from;
		return this;
	}

	public PayloadMessageBuilder to(Address to){
		this.to = to;
		return this;
	}

	public PayloadMessageBuilder request(){
		this.uuid = UUID.randomUUID();
		this.type = MessageType.REQUEST;
		return this;
	}

	public PayloadMessageBuilder response(PayloadMessage request){
		if (request.getType().equals(MessageType.REQUEST)){
			this.uuid = request.getUuid();
			this.type = MessageType.RESPONSE;
			this.from = request.getTo();
			this.to = request.getFrom();
		}
		return this;
	}

	public PayloadMessageBuilder reset(){
		this.uuid = null;
		this.type = null;
		this.contentType = null;
		this.content = null;
		this.from = null;
		this.to = null;

		return this;
	}

	public PayloadMessage build(){
		exceptionBuilder
			.checkField("uuid", uuid)
			.checkField("type", type)
			.checkField("contentType", contentType)
			.checkField("content", content)
			.checkField("from", from)
			.checkField("to", to);

		Optional<RuntimeException> maybeException = exceptionBuilder
			.checkType(type)
			.checkContentType(contentType)
			.checkContent(content)
			.build();
		if (maybeException.isPresent()){
			throw maybeException.get();
		}

		return new PayloadMessageImpl(uuid, type, contentType, content, from, to);
	}

	private static class ExceptionBuilder extends AbsentFieldRuntimeExceptionBuilderImpl{
		public ExceptionBuilder(Function<String, RuntimeException> creator) {
			super(creator);
		}

		public ExceptionBuilder checkType(MessageType type){
			if (type != null && !type.equals(MessageType.REQUEST) && !type.equals(MessageType.RESPONSE)){
				appendPartDelimiterAndGet().append("type is invalid it must be either REQUEST or RESPONSE");
			}
			return this;
		}


		public ExceptionBuilder checkContentType(String contentType) {
			if (contentType != null && contentType.isBlank()){
				appendPartDelimiterAndGet().append("contentType must not be empty");
			}
			return this;
		}

		public ExceptionBuilder checkContent(String content) {
			if (content != null && content.isBlank()){
				appendPartDelimiterAndGet().append("content must not be empty");
			}
			return this;
		}
	}
}
