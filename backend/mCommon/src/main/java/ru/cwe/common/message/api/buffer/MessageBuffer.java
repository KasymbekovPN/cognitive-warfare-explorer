package ru.cwe.common.message.api.buffer;

import ru.cwe.common.control.Shutdownable;
import ru.cwe.common.record.api.Record;

public interface MessageBuffer<R extends Record> extends Shutdownable {
	boolean offer(R record);
}
