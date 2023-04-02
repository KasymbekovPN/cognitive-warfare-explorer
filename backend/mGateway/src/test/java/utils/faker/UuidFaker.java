package utils.faker;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public final class UuidFaker {
	private final Faker core;

	public UUID uuid(){
		return UUID.randomUUID();
	}
}
