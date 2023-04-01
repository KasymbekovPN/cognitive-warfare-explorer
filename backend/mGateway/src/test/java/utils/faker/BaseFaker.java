package utils.faker;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public final class BaseFaker {
	private final Faker core;

	public int digit(){
		return core.number().randomDigit();
	}

	public int numberBetween(int min, int max){
		return core.number().numberBetween(min, max);
	}

	public String string(){
		return "randomString" + String.valueOf(core.number().randomNumber());
	}

	public UUID uuid(){
		return UUID.randomUUID();
	}
}
