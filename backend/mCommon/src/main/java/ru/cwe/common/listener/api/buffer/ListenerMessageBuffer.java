package ru.cwe.common.listener.api.buffer;

import ru.cwe.common.record.api.Record;

// TODO: 07.07.2023 temp
public interface ListenerMessageBuffer {
	boolean offer(Record record);
}
