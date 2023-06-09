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

	public static LongFaker long_() {
		return new LongFaker(new Faker());
	}

	public static DoubleFaker double_(){
		return new DoubleFaker(new Faker());
	}

	public static ByteFaker byte_(){
		return new ByteFaker(new Faker());
	}
}
