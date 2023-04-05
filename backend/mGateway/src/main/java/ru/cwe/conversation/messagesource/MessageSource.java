package ru.cwe.conversation.messagesource;

public interface MessageSource<M> {
	M next();
}
