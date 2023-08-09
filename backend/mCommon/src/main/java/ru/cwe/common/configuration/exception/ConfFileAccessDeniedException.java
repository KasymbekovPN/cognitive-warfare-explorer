package ru.cwe.common.configuration.exception;

public final class ConfFileAccessDeniedException extends RuntimeException{
	public ConfFileAccessDeniedException(String message) {
		super(message);
	}
}
