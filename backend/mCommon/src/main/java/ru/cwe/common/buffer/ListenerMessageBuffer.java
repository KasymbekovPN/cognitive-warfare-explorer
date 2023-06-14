package ru.cwe.common.buffer;

import ru.cwe.common.record.ListenerRecord;

public interface ListenerMessageBuffer {
	boolean offer(ListenerRecord record);
}
