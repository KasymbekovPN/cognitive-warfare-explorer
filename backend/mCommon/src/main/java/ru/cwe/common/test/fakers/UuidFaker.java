package ru.cwe.common.test.fakers;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public final class UuidFaker {
	private final Faker faker;

	public UUID random(){
		return UUID.randomUUID();
	}
}
