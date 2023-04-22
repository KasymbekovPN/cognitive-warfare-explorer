package ru.cwe.common.test.faker;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class StringFaker {
	private final Faker core;

	public String string(){
		return "randomString" + String.valueOf(core.number().randomNumber());
	}
}
