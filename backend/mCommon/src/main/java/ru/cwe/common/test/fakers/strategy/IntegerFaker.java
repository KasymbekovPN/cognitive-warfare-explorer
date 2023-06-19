package ru.cwe.common.test.fakers.strategy;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class IntegerFaker {
	private final Faker core;

	public int random(){
		return core.number().randomDigit();
	}

	public int between(int min, int max){
		return core.number().numberBetween(min, max);
	}

	public int greater(int threshold){
		return core.number().numberBetween(threshold, Integer.MAX_VALUE);
	}

	public int less(int threshold){
		return core.number().numberBetween(Integer.MIN_VALUE, threshold);
	}
}
