package ru.cwe.common.message.payload;

import ru.cwe.common.address.Address;
import ru.cwe.common.message.Message;

public interface PayloadMessage extends Message {
	String getContentType();
	String getContent();
	Address getFrom();
	Address getTo();
}
