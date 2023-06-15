package ru.cwe.common.listener;

import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

// TODO: 15.06.2023 ???
@RequiredArgsConstructor
public class RestartableListenerOldImpl implements RestartableListenerOld {
	private final Supplier<ListenerThreadOld> threadSupplier;

	private ListenerThreadOld thread;

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
		ListenerThreadOld th = null;
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
