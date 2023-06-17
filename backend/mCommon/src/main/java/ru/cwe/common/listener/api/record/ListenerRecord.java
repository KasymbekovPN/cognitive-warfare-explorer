package ru.cwe.common.listener.api.record;

import ru.cwe.common.message.api.message.Message;

import java.util.UUID;

public interface ListenerRecord {
	UUID key();
	Message value();
	<T> T get(String property, Class<T> type);
}
