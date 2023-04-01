package ru.cwe.conversation.message;

import java.util.HashMap;
import java.util.Map;

public final class Priorities {
	private static final int LESS_CHECK_RESULT = -1;
	private static final int EQ_CHECK_RESULT = 0;
	private static final int MORE_CHECK_RESULT = 1;

	public static final int MIN = 0;
	public static final int MAX = 127;

	private static final Map<Integer, Integer> HOLD_RESULTS = new HashMap<>(){{
		put(LESS_CHECK_RESULT, MIN);
		put(MORE_CHECK_RESULT, MAX);
	}};

	public static int check(int priority){
		if (priority < MIN) return LESS_CHECK_RESULT;
		else if (priority > MAX) return MORE_CHECK_RESULT;
		return EQ_CHECK_RESULT;
	}

	public static int adjust(int priority){
		return HOLD_RESULTS.getOrDefault(check(priority), priority);
	}
}
