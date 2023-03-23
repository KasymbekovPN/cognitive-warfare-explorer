// TODO: 23.03.2023 ???

//package ru.cwe.conversation.type;
//
//import java.util.Optional;
//
//// TODO: 23.03.2023 del
//public final class MessageTypeBuilder {
//	private String type;
//
//	public MessageTypeBuilder type(String type){
//		this.type = type;
//		return this;
//	}
//
//	public ContentType build(){
//		Optional<RuntimeException> maybeException = checkType();
//		if (maybeException.isPresent()) {
//			throw maybeException.get();
//		}
//		return new ContentTypeImpl(type);
//	}
//
//	private Optional<RuntimeException> checkType() {
//		if (type == null){
//			return Optional.of(new MessageTypeBuildingRuntimeException("Type is null"));
//		}
//		if (type.isBlank()){
//			return Optional.of(new MessageTypeBuildingRuntimeException("Type is wrong"));
//		}
//		return Optional.empty();
//	}
//}
