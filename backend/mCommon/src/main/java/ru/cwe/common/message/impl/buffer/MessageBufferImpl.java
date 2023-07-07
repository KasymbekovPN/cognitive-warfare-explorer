package ru.cwe.common.message.impl.buffer;

import lombok.extern.slf4j.Slf4j;
import ru.cwe.common.message.api.buffer.MessageBuffer;
import ru.cwe.common.record.api.Record;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@Slf4j
public class MessageBufferImpl<R extends Record> implements MessageBuffer<R> {
	private final AtomicBoolean started = new AtomicBoolean(true);
	private final BlockingQueue<R> queue;
	private final ExecutorService es;
	private final Consumer<R> action;
	private final String name;

	public static <R extends Record> MessageBufferImpl<R> instance(BlockingQueue<R> queue,
																   ExecutorService es,
																   Consumer<R> action,
																   String name){
		MessageBufferImpl<R> instance = new MessageBufferImpl<>(queue, es, action, name);
		instance.submit(instance::execute);
		log.info("[{}] is created", name);
		return instance;
	}

	private MessageBufferImpl(BlockingQueue<R> queue, ExecutorService es, Consumer<R> action, String name) {
		this.queue = queue;
		this.es = es;
		this.action = action;
		this.name = name;
	}


	@Override
	public boolean offer(R record) {
		if (started.get()){
			return queue.offer(record);
		} else {
			log.warn("[{}] is shutdown, the attempt of append is rejected...", name);
			return false;
		}
	}

	@Override
	public void shutdown(Object... args) {
		started.set(false);
		es.shutdown();
		log.info("[{}] is shutdown", name);
	}

	private void submit(Runnable method){
		es.submit(method);
	}

	private void execute(){
		log.info("[{}] Execution is started", name);
		while (started.get()){
			try {
				R record = queue.take();
				action.accept(record);
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
				Thread.currentThread().interrupt();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		log.info("[{}] Execution is finished", name);
	}
}
