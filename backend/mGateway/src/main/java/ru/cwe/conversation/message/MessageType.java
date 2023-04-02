package ru.cwe.conversation.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum MessageType {
	INVALID(0),
	REQUEST(1),
	RESPONSE(2),
	CONFIRMATION(3);

	private static final Map<Integer, String> MESSAGE_TYPE_MAP = new HashMap<>(){{
		put(1, "REQUEST");
		put(2, "RESPONSE");
		put(3, "CONFIRMATION");
	}};

	public static MessageType valueOf(int value){
		return valueOf(MESSAGE_TYPE_MAP.containsKey(value) ? MESSAGE_TYPE_MAP.get(value) : INVALID.name());
	}

	public static boolean check(int value){
		return value >= INVALID.getValue() && value <= CONFIRMATION.getValue();
	}

	@Getter
	private final int value;
}
