package ru.cwe.common.test.faker;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class BaseFaker {
	private final Faker core;

	public IntegerFaker integer(){
		return new IntegerFaker(core);
	}

	public StringFaker string(){
		return new StringFaker(core);
	}

	public UuidFaker uuid(){
		return new UuidFaker(core);
	}
}
