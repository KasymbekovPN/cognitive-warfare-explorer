package ru.cwe.conversation.message;

public final class Versions {
	public static final int MIN = 0;
	public static final int MAX = 1023;

	public static boolean check(int version){
		return version >= MIN && version <= MAX;
	}
}
