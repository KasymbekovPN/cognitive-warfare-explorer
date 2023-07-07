package ru.cwe.common.record.api;

import ru.cwe.common.message.api.message.Message;

import java.util.UUID;

public interface Record {
	UUID key();
	Message value();
	<T> T get(String property, Class<T> type);
}
