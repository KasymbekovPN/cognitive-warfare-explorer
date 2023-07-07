package ru.cwe.common.listener.api.listener;

import ru.cwe.common.listener.api.record.ListenerRecord;

import java.io.Closeable;
import java.util.List;

public interface PollingListener extends Listener, Closeable {
	List<ListenerRecord> poll();
	void close();
}
