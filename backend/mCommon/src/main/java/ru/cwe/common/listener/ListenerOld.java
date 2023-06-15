package ru.cwe.common.listener;

import ru.cwe.common.listener.api.record.ListenerRecord;

import java.io.Closeable;
import java.util.List;

// TODO: 15.06.2023 ???
public interface ListenerOld extends Closeable {
	void subscribe();
	void unsubscribe();
	List<ListenerRecord> poll();
}
