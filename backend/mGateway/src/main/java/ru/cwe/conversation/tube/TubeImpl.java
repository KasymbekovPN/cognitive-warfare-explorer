package ru.cwe.conversation.tube;

import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.tube.datum.TubeDatum;
import ru.cwe.conversation.tube.datum.TubeDatumImpl;
import ru.cwe.conversation.tube.listener.TubeListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public final class TubeImpl implements Tube {
	private final BlockingQueue<TubeDatum> queue;
	private final List<TubeListener> puttingListeners;
	private final List<TubeListener> takingListeners;

	public static TubeImpl instance(){
		return builder().build();
	}

	public static Builder builder(){
		return new Builder();
	}

	private TubeImpl(final BlockingQueue<TubeDatum> queue,
			      	 final List<TubeListener> puttingListeners,
					 final List<TubeListener> takingListeners) {
		this.queue = queue;
		this.puttingListeners = puttingListeners;
		this.takingListeners = takingListeners;
	}

	@Override
	public boolean put(final PayloadMessage message) {
		return put(new TubeDatumImpl(message));
	}

	@Override
	public boolean put(final TubeDatum datum) {
		synchronized (this){
			for (TubeListener listener : puttingListeners) {
				listener.listen(datum);
			}
		}
		return queue.offer(datum);
	}

	@Override
	public TubeDatum take() throws InterruptedException {
		TubeDatum datum = queue.take();
		synchronized (this){
			for (TubeListener listener : takingListeners) {
				listener.listen(datum);
			}
		}
		return datum;
	}

	@Override
	public synchronized void addPuttingListener(TubeListener listener) {
		if (!this.puttingListeners.contains(listener)){
			this.puttingListeners.add(listener);
		}
	}

	@Override
	public synchronized void erasePuttingListener(TubeListener listener) {
		this.puttingListeners.remove(listener);
	}

	@Override
	public synchronized void addTakingListener(TubeListener listener) {
		if (!this.takingListeners.contains(listener)){
			this.takingListeners.add(listener);
		}
	}

	@Override
	public synchronized void eraseTakingListener(TubeListener listener) {
		this.takingListeners.remove(listener);
	}

	public static class Builder {
		private static final int INIT_CAPACITY = 10_000;

		private final List<TubeListener> puttingListeners = new ArrayList<>();
		private final List<TubeListener> takingListeners = new ArrayList<>();
		private BlockingQueue<TubeDatum> queue;


		public Builder queue(BlockingQueue<TubeDatum> queue){
			this.queue = queue;
			return this;
		}

		public Builder puttingListener(TubeListener listener){
			if (!this.puttingListeners.contains(listener)){
				this.puttingListeners.add(listener);
			}
			return this;
		}

		public Builder takingListener(TubeListener listener){
			if (!this.takingListeners.contains(listener)){
				this.takingListeners.add(listener);
			}
			return this;
		}

		public TubeImpl build(){
			checkQueue();
			return new TubeImpl(queue, puttingListeners, takingListeners);
		}

		private void checkQueue() {
			if (this.queue == null){
				this.queue = new PriorityBlockingQueue<>(INIT_CAPACITY, new Comparator<TubeDatum>() {
					@Override
					public int compare(TubeDatum o1, TubeDatum o2) {
						return -1 * Integer.compare(o1.getMessage().getPriority(), o2.getMessage().getPriority());
					}
				});
			}
		}
	}
}
