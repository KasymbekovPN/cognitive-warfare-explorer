package ru.cwe.common.listener;

import ru.cwe.common.record.ListenerRecord;

import java.io.Closeable;
import java.util.List;

public interface Listener extends Closeable {
	void subscribe();
	void unsubscribe();
	List<ListenerRecord> poll();
}
