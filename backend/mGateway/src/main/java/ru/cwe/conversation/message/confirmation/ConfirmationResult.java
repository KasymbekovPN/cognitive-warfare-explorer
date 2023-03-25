package ru.cwe.conversation.message.confirmation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum ConfirmationResult {
	INVALID(0),
	REQUEST(1),
	RESPONSE(2);

	private static final Map<Integer, String> CREATION_MAP = new HashMap<>(){{
		put(0, "INVALID");
		put(1, "REQUEST");
		put(2, "RESPONSE");
	}};

	private static final int MIN_VALUE = INVALID.getValue();
	private static final int MAX_VALUE = RESPONSE.getValue();
	private static final int DEFAULT_VALUE = MIN_VALUE;

	@Getter
	private final int value;

	public static ConfirmationResult valueOf(int value){
		if (value < MIN_VALUE || value > MAX_VALUE){
			value = DEFAULT_VALUE;
		}
		return ConfirmationResult.valueOf(CREATION_MAP.get(value));
	}
}
