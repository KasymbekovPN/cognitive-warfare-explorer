package ru.cwe.conversation.type;

import java.util.Optional;

public final class MessageTypeBuilder {
	private String type;

	public MessageTypeBuilder type(String type){
		this.type = type;
		return this;
	}

	public MessageType build(){
		Optional<RuntimeException> maybeException = checkType();
		if (maybeException.isPresent()) {
			throw maybeException.get();
		}
		return new MessageTypeImpl(type);
	}

	private Optional<RuntimeException> checkType() {
		if (type == null){
			return Optional.of(new MessageTypeBuildingRuntimeException("Type is null"));
		}
		if (type.isBlank()){
			return Optional.of(new MessageTypeBuildingRuntimeException("Type is wrong"));
		}
		return Optional.empty();
	}
}
