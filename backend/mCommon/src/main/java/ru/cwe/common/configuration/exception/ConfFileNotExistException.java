package ru.cwe.common.configuration.exception;

public final class ConfFileNotExistException extends RuntimeException{
	public ConfFileNotExistException(String message) {
		super(message);
	}
}
