package ru.cwe.conversation.message.payload;

import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.Message;

public interface PayloadMessage extends Message {
	boolean isResponse();
	String getContentType();
	String getContent();
	Address getFrom();
	Address getTo();
}