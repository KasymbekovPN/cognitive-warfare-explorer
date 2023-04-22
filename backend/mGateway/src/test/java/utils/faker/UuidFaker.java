package utils.faker;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

// TODO: 22.04.2023 del
@RequiredArgsConstructor
public final class UuidFaker {
	private final Faker core;

	public UUID uuid(){
		return UUID.randomUUID();
	}
}
