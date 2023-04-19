package ru.cwe.conversation.tube.listener;

import lombok.Getter;
import ru.cwe.conversation.tube.TubeDatum;

import java.util.concurrent.atomic.AtomicInteger;

public final class TotalCounterTubeListener implements TubeListener {
	@Getter
	private final AtomicInteger counter = new AtomicInteger(0);

	@Override
	public void listen(TubeDatum datum) {
		counter.incrementAndGet();
	}
}
