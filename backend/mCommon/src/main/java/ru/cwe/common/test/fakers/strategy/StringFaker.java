package ru.cwe.common.test.fakers.strategy;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringFaker {
	public static final int MIN_LEN = 1;
	public static final int MAX_LEN = 10;
	public static final int MIN_CHAR_CODE = 32;
	public static final int MAX_CHAR_CODE = 126;

	private final Faker core;

	public String random(){
		int length = core.number().numberBetween(MIN_LEN, MAX_LEN + 1);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append((char) core.number().numberBetween(MIN_CHAR_CODE, MAX_CHAR_CODE + 1));
		}

		return builder.toString();
	}
}
