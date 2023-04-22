package ru.cwe.conversation.tube;

import ru.cwe.conversation.tube.creator.DatumCreator;
import ru.cwe.conversation.tube.datum.TubeDatum;

// TODO: 18.04.2023 del
public interface TubeOld {
	boolean put(TubeDatum datum);
	void dispose() throws InterruptedException;
	int size();
	DatumCreator creator();
}
