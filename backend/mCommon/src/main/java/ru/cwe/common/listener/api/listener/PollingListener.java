package ru.cwe.common.listener.api.listener;

import ru.cwe.common.record.api.Record;

import java.io.Closeable;
import java.util.List;

public interface PollingListener extends Listener, Closeable {
	List<Record> poll();
	void close();
}
