package ru.cwe.conversation.message;

public final class Versions {
	private static final int MIN = 0;
	private static final int MAX = 1023;

	public static boolean check(int version){
		return version >= MIN && version <= MAX;
	}
}
