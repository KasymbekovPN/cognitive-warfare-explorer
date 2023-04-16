package ru.cwe.conversation.tube;

public interface Tube {
	void send(TubeDatum datum);
	int size();
	DatumCreator creator();
}
