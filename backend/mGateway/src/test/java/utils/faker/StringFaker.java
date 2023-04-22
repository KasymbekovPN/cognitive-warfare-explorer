package utils.faker;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

// TODO: 22.04.2023 del
@RequiredArgsConstructor
public final class StringFaker {
	private final Faker core;

	public String string(){
		return "randomString" + String.valueOf(core.number().randomNumber());
	}
}
