package ru.cwe.common.listener.api.buffer;

import ru.cwe.common.listener.api.record.ListenerRecord;

public interface ListenerMessageBuffer {
	boolean offer(ListenerRecord record);
}
