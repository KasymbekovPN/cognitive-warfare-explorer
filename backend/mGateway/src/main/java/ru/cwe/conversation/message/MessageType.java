package ru.cwe.conversation.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageType {
	REQUEST(0),
	RESPONSE(1),
	CONFIRMATION(2);

	@Getter
	private final int value;
}
