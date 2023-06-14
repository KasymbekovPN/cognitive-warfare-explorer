package ru.cwe.common.record;

import ru.cwe.common.message.Message;

import java.util.UUID;

public interface ListenerRecord {
	UUID key();
	Message value();
	<T> T get(String property, Class<T> type);
}
