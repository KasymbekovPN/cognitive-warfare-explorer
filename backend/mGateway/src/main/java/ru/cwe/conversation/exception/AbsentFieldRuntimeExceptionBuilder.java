package ru.cwe.conversation.exception;

public interface AbsentFieldRuntimeExceptionBuilder extends RuntimeExceptionBuilder{
	AbsentFieldRuntimeExceptionBuilder checkField(String name, Object value);
}
