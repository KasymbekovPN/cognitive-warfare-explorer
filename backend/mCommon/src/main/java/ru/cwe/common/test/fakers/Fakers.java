package ru.cwe.common.test.fakers;

import com.github.javafaker.Faker;

public final class Fakers {

	public static IntegerFaker integer(){
		return new IntegerFaker(new Faker());
	}

	public static StringFaker str(){
		return new StringFaker(new Faker());
	}

	public static UuidFaker uuid(){
		return new UuidFaker(new Faker());
	}
}
