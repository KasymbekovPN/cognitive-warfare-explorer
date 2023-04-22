package utils.faker;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

// TODO: 22.04.2023 del
@RequiredArgsConstructor
public final class BaseFaker {
	private final Faker core;

	public NumberFaker number(){
		return new NumberFaker(core);
	}

	public StringFaker string(){
		return new StringFaker(core);
	}

	public UuidFaker uuid(){
		return new UuidFaker(core);
	}
}
