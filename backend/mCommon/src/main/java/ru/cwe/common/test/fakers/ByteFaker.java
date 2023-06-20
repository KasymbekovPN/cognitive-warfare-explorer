package ru.cwe.common.test.fakers;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ByteFaker {
	private final Faker core;

	public byte random(){
		return (byte) core.number().numberBetween(Byte.MIN_VALUE, Byte.MAX_VALUE);
	}

	public byte[] array(int size){
		int s = Math.max(size, 1);
		byte[] result = new byte[s];
		for (int i = 0; i < s; i++) {
			result[i] = random();
		}

		return result;
	}
}
