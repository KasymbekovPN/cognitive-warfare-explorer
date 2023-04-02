package utils.faker;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

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
