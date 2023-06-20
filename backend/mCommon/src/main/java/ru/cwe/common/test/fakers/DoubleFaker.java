package ru.cwe.common.test.fakers;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public final class DoubleFaker {
	public static final long MIN = Long.MIN_VALUE;
	public static final long MAX = Long.MAX_VALUE;
	public static final int DEFAULT_MAX_NUMBER_OF_DECIMALS = 10;

	private final Faker core;

	public double random(){
		return core.number().randomDouble(DEFAULT_MAX_NUMBER_OF_DECIMALS, MIN, MAX);
	}

	public List<Double> list(int size){
		ArrayList<Double> result = new ArrayList<>();
		for (int i = 0; i < Math.max(size, 1); i++) {
			result.add(random());
		}
		return result;
	}
}
