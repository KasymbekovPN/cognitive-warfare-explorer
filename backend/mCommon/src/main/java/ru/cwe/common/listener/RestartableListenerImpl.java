package ru.cwe.common.listener;

import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class RestartableListenerImpl implements RestartableListener {
	private final Supplier<ListenerThread> threadSupplier;

	private ListenerThread thread;

	@Override
	public void start() {
		boolean notNull = false;
		synchronized (this){
			if (thread == null){
				thread = threadSupplier.get();
				notNull = thread != null;
			}
		}
		if (notNull){
			thread.start();
		}
	}

	@Override
	public void shutdown() {
		ListenerThread th = null;
		synchronized (this){
			if (thread != null){
				th = thread;
				thread = null;
			}
		}
		if (th != null){
			th.shutdown();
		}
	}
}
