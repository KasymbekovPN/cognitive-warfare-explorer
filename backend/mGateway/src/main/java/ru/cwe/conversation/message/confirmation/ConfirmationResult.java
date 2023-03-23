package ru.cwe.conversation.message.confirmation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConfirmationResult {
	REQUEST(0),
	RESPONSE(1),
	INVALID(2);

	@Getter
	private final int value;
}
