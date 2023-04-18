package ru.cwe.conversation.tube;

// TODO: 18.04.2023 del
public interface TubeOld {
	boolean put(TubeDatum datum);
	void dispose() throws InterruptedException;
	int size();
	DatumCreator creator();
}
