package ru.cwe.common.listener;

import lombok.RequiredArgsConstructor;
import ru.cwe.common.buffer.ListenerMessageBuffer;
import ru.cwe.common.record.ListenerRecord;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

// TODO: 14.06.2023 rename
@RequiredArgsConstructor
public class KafkaListenerThread extends Thread implements ListenerThread {
	private final AtomicBoolean keepListening = new AtomicBoolean(false);

	private final Supplier<Listener> listenerSupplier;
	private final ListenerMessageBuffer buffer;

	@Override
	public void start() {
		// TODO: 14.06.2023 test
//		if(!keepListening.getAndSet(true)){
//			super.start();
//		}
	}

	@Override
	public void shutdown() {
		// TODO: 14.06.2023 test
//		keepListening.set(false);
	}

	@Override
	public void run() {
		// TODO: 14.06.2023 test
//		try(Listener listener = listenerSupplier.get()){
//			listener.subscribe();
//
//			while (keepListening.get()){
//				List<ListenerRecord> records = listener.poll();
//				records.forEach(buffer::offer);
//			}
//			listener.unsubscribe();
//		} catch (IOException ex){
//			shutdown();
//		}
	}
}
