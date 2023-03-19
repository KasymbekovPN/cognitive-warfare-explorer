package ru.cwe.conversation.message;

import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.type.MessageType;

import java.util.UUID;

public interface Message {
	boolean isResponse();
	UUID getUuid();
	MessageType getType();
	String getContent();
	Address getFrom();
	Address getTo();
}
