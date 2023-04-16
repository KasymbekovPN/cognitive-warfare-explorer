package ru.cwe.conversation.tube;

import ru.cwe.conversation.message.payload.PayloadMessage;

public class TubeOldImpl implements TubeOld {
	@Override
	public void send(PayloadMessage message) {

	}

	@Override
	public int size() {
		return 0;
	}

// TODO: 16.04.2023 del
//	package kpn.ctrlf.client.conversation.notifier;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//@Slf4j
//public final class DomainChangeNotifierImpl implements DomainChangeNotifier {
//
//	private final AtomicBoolean inProcess = new AtomicBoolean(true);
//	private final BlockingQueue<DomainChangeNotificationTask> taskQueue;
//	private final ExecutorService taskExecutor;
//	private final Sender sender;
//
//	public static DomainChangeNotifier create(BlockingQueue<DomainChangeNotificationTask> taskQueue,
//											  ExecutorService taskProcessor,
//											  Sender sender){
//		DomainChangeNotifierImpl notifier = new DomainChangeNotifierImpl(taskQueue, taskProcessor, sender);
//		notifier.submit(notifier::executeTask);
//		log.info("It's created");
//		return notifier;
//	}
//
//	private DomainChangeNotifierImpl(BlockingQueue<DomainChangeNotificationTask> taskQueue,
//									 ExecutorService taskExecutor,
//									 Sender sender) {
//		this.taskQueue = taskQueue;
//		this.taskExecutor = taskExecutor;
//		this.sender = sender;
//	}
//
//	@Override
//	public boolean append(DomainChangeNotificationTask task) {
//		if (inProcess.get()){
//			return taskQueue.offer(task);
//		} else {
//			log.warn("It's disposed, the attempt of append is rejected...");
//			return false;
//		}
//	}
//
//	@Override
//	public void dispose() throws InterruptedException {
//		inProcess.set(false);
//		taskExecutor.shutdown();
//		log.info("It's disposed");
//	}
//
//	private void submit(Runnable method) {
//		taskExecutor.submit(method);
//	}
//
//	private void executeTask(){
//		log.info("Task execution is started");
//		while (inProcess.get()){
//			try {
//				DomainChangeNotificationTask task = taskQueue.take();
//				sender.send(task);
//			} catch (InterruptedException e) {
//				log.error(e.getMessage(), e);
//				Thread.currentThread().interrupt();
//			} catch (Exception e) {
//				log.error(e.getMessage(), e);
//			}
//		}
//		log.info("Task execution is finished");
//	}
//}
}
