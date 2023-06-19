package ru.cwe.common.test.fakers;

import com.github.javafaker.Faker;

public final class Fakers {

	public static IntegerFaker int_(){
		return new IntegerFaker(new Faker());
	}

	public static StringFaker str_(){
		return new StringFaker(new Faker());
	}

	public static UuidFaker uuid_(){
		return new UuidFaker(new Faker());
	}

	// TODO: 19.06.2023 !!!
	// long
	// double + list
	// byte + array
}
