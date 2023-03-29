package ru.cwe.conversation.message.confirmation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum ConfirmationResult {
	INVALID(-1),
	REQUEST(0),
	RESPONSE(1);

	@Getter
	private final int value;

	private static final Map<Integer, String> CONFIRMATION_RESULT_MAP = new HashMap<>(){{
		put(0, "REQUEST");
		put(1, "RESPONSE");
	}};

	public static ConfirmationResult valueOf(int value){
		return valueOf(CONFIRMATION_RESULT_MAP.containsKey(value) ? CONFIRMATION_RESULT_MAP.get(value) : INVALID.name());
	}
}
