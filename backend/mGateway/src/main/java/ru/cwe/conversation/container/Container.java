package ru.cwe.conversation.container;

public interface Container<M> {
	void append(M message);
}
