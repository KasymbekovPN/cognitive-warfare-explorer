package ru.cwe.conversation.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageType {
	REQUEST(0),
	RESPONSE(1),
	CONFIRMATION(2);

	// TODO: 25.03.2023 create valueOf(int)

	@Getter
	private final int value;
}
