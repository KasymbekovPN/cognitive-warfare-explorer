package ru.cwe.common.listener.impl;

import lombok.RequiredArgsConstructor;
import ru.cwe.common.listener.api.Listener;
import ru.cwe.common.listener.api.PollingListener;
import ru.cwe.common.listener.api.buffer.ListenerMessageBuffer;
import ru.cwe.common.listener.api.factory.ListenerFactory;
import ru.cwe.common.listener.api.record.ListenerRecord;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
public final class RestartableListener implements Listener {
	private final AtomicBoolean keepListening = new AtomicBoolean(false);

	private final ListenerFactory<PollingListener> factory;
	private final ListenerMessageBuffer buffer;

	private Thread thread;

	@Override
	public synchronized void subscribe() {
		if (thread == null){
			thread = new Thread(() -> {
				try(PollingListener listener = factory.create()){
					listener.subscribe();
					while (keepListening.get()){
						List<ListenerRecord> records = listener.poll();
						records.forEach(buffer::offer);
					}
					listener.unsubscribe();
				}
			});

			keepListening.set(true);
			thread.start();
		}
	}

	@Override
	public synchronized void unsubscribe() {
		if (thread != null){
			keepListening.set(false);
			thread = null;
		}
	}
}
