package utils.faker;

import com.github.javafaker.Faker;

// TODO: 22.04.2023 del
public final class Fakers {
	private static final Faker core = new Faker();

	public static Faker core(){
		return core;
	}

	public static BaseFaker base(){
		return new BaseFaker(core());
	}

	public static AddressFaker address(){
		return new AddressFaker(base());
	}

	public static MessageFaker message(){
		return new MessageFaker(base());
	}
}
