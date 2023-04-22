package utils.faker;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

// TODO: 22.04.2023 del
@RequiredArgsConstructor
public final class NumberFaker {
	private final Faker core;

	public int integer(){
		return core.number().randomDigit();
	}

	public int between(int min, int max){
		return core.number().numberBetween(min, max);
	}

	public int lessThan(int threshold){
		return core.number().numberBetween(Integer.MIN_VALUE, threshold);
	}

	public int moreThan(int threshold){
		return core.number().numberBetween(threshold + 1, Integer.MAX_VALUE);
	}
}
