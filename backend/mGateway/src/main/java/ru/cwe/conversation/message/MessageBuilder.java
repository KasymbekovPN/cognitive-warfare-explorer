package ru.cwe.conversation.message;

import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.type.MessageType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class MessageBuilder {
	private final List<String> absentFields = new ArrayList<>();

	private boolean isResponse;
	private UUID uuid;
	private MessageType type;
	private String content;
	private Address from;
	private Address to;

	public MessageBuilder() {
		this.uuid = UUID.randomUUID();
	}

	public MessageBuilder(Message request) {
		this.isResponse = true;
		this.uuid = request.getUuid();
		this.from = request.getTo();
		this.to = request.getFrom();
	}

	public MessageBuilder uuid(UUID uuid){
		this.uuid = uuid;
		return this;
	}

	public MessageBuilder response(){
		this.isResponse = true;
		return this;
	}

	public MessageBuilder request(){
		this.isResponse = false;
		return this;
	}

	public MessageBuilder type(MessageType type){
		this.type = type;
		return this;
	}

	public MessageBuilder content(String content){
		this.content = content;
		return this;
	}

	public MessageBuilder from(Address from){
		this.from = from;
		return this;
	}

	public MessageBuilder to(Address to) {
		this.to = to;
		return this;
	}

	public Message build(){
		checkType();
		checkContent();
		checkFrom();
		checkTo();
		if (absentFields.size() > 0){
			throw new MessageBuildingRuntimeException(createExceptionMessage());
		}
		return new MessageImpl(isResponse, uuid, type, content, from, to);
	}

	private void checkType() {
		if (type == null){ absentFields.add("type"); }
	}

	private void checkContent() {
		if (content == null){ absentFields.add("content"); }
	}

	private void checkFrom() {
		if (from == null) { absentFields.add("from"); }
	}

	private void checkTo() {
		if (to == null) { absentFields.add("to"); }
	}

	private String createExceptionMessage() {
		String delimiter = "";
		StringBuilder sb = new StringBuilder("Absent fields: ");
		for (String absentField : absentFields) {
			sb.append(delimiter).append(absentField);
			delimiter = " & ";
		}
		return sb.toString();
	}
}
