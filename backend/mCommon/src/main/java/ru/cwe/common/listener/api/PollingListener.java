package ru.cwe.common.listener.api;

import ru.cwe.common.listener.api.record.ListenerRecord;

import java.util.List;

public interface PollingListener extends Listener {
	List<ListenerRecord> poll();
}
