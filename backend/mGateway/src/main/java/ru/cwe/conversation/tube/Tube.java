package ru.cwe.conversation.tube;

import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.tube.datum.TubeDatum;
import ru.cwe.conversation.tube.listener.TubeListener;

public interface Tube {
	boolean put(PayloadMessage message);
	boolean put(TubeDatum datum);
	TubeDatum take() throws InterruptedException;
	default void addPuttingListener(TubeListener listener) {};
	default void erasePuttingListener(TubeListener listener) {};
	default void addTakingListener(TubeListener listener) {};
	default void eraseTakingListener(TubeListener listener) {};
}
