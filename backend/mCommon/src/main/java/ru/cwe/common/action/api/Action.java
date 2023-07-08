package ru.cwe.common.action.api;

public interface Action<T> {
	void execute(T value);
}
