package ru.cwe.conversation.container;

public interface MessageContainer<M> {
	void append(M message);
}
