package ru.cwe.conversation.tube.listener;

import ru.cwe.conversation.tube.datum.TubeDatum;

public interface TubeListener {
	void listen(TubeDatum datum);
}
