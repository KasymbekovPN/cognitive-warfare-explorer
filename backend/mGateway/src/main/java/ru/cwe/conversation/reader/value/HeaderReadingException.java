package ru.cwe.conversation.reader.value;

// TODO: 01.04.2023 del comment
// version - 10 bit
// priority - 8 bit
// type - 7 bit
// result - 7 bit

public final class HeaderReadingException extends RuntimeException{
	public HeaderReadingException(String message) {
		super(message);
	}
}
