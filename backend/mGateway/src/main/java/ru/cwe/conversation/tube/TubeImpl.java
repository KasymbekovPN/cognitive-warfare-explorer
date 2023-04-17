package ru.cwe.conversation.tube;

import lombok.extern.slf4j.Slf4j;
import ru.cwe.conversation.gateway.out.OutGateway;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public final class TubeImpl implements Tube {
	private final AtomicBoolean inProcess = new AtomicBoolean(true);
	private final BlockingQueue<TubeDatum> queue;
	private final ExecutorService executor;
	private final OutGateway outGateway;

	public static TubeImpl create(BlockingQueue<TubeDatum> queue,
								  ExecutorService executor,
								  OutGateway outGateway){
		TubeImpl tube = new TubeImpl(queue, executor, outGateway);
		tube.submit(tube::execute);
		log.info("It's started...");
		return tube;
	}

	private TubeImpl(BlockingQueue<TubeDatum> queue, ExecutorService executor, OutGateway outGateway) {
		this.queue = queue;
		this.executor = executor;
		this.outGateway = outGateway;
	}

	@Override
	public boolean put(TubeDatum datum) {
		if (inProcess.get()){
			return queue.offer(datum);
		}
		log.warn("It's disposed, the attempt of append is rejected...");
		return false;
	}

	@Override
	public void dispose() throws InterruptedException {
		inProcess.set(false);
		executor.shutdown();
		outGateway.shutdown();
		log.info("It's disposed");
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public DatumCreator creator() {
		return new DatumCreatorImpl(this);
	}

	private void submit(Runnable method){
		executor.submit(method);
	}

	private void execute(){
		log.info("Execution is started...");
		while (inProcess.get()){
			try {
				TubeDatum datum = queue.take();
				outGateway.send(datum.getMessage(), datum.getHost(), datum.getPort());
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
				Thread.currentThread().interrupt();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		log.info("Execution is finished...");
	}
}
