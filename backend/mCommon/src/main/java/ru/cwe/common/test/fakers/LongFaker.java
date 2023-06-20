package ru.cwe.common.test.fakers;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class LongFaker {
	private final Faker core;

	public long random(){
		return core.number().randomNumber();
	}

	public long between(long min, long max){
		return core.number().numberBetween(min, max);
	}

	public long greater(long threshold){
		return core.number().numberBetween(threshold, Long.MAX_VALUE);
	}

	public long less(long threshold){
		return core.number().numberBetween(Long.MIN_VALUE, threshold);
	}
}
