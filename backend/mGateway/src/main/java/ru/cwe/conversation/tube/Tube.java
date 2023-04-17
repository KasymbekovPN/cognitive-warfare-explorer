package ru.cwe.conversation.tube;

public interface Tube {
	boolean put(TubeDatum datum);
	void dispose() throws InterruptedException;
	int size();
	DatumCreator creator();
}
