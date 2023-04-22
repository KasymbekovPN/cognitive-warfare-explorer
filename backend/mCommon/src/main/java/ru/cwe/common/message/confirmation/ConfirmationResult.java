package ru.cwe.common.message.confirmation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum ConfirmationResult {
	INVALID(0),
	REQUEST(1),
	RESPONSE(2);

	@Getter
	private final int value;

	private static final Map<Integer, String> CONFIRMATION_RESULT_MAP = new HashMap<>(){{
		put(1, "REQUEST");
		put(2, "RESPONSE");
	}};

	public static ConfirmationResult valueOf(final int value){
		return valueOf(CONFIRMATION_RESULT_MAP.containsKey(value) ? CONFIRMATION_RESULT_MAP.get(value) : INVALID.name());
	}

	public static boolean check(final int value){
		return value >= INVALID.getValue() && value <= RESPONSE.getValue();
	}
}
