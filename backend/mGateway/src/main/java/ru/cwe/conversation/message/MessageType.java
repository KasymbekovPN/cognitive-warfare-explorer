package ru.cwe.conversation.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public enum MessageType {
	INVALID(-1),
	REQUEST(0),
	RESPONSE(1),
	CONFIRMATION(2);

	private static final Map<Integer, String> MESSAGE_TYPE_MAP = new HashMap<>(){{
		put(0, "REQUEST");
		put(1, "RESPONSE");
		put(2, "CONFIRMATION");
	}};

	public static MessageType valueOf(int value){
		return valueOf(MESSAGE_TYPE_MAP.containsKey(value) ? MESSAGE_TYPE_MAP.get(value) : INVALID.name());
	}

	@Getter
	private final int value;
}
