package ru.cwe.common.port;

public final class Ports {
	private static final int MIN = 0;
	private static final int MAX = 65535;
	private static final int LEFT_TO_RANGE = -1;
	private static final int IN_RANGE = 0;
	private static final int RIGHT_TO_RANGE = 1;

	public static int checkInRange(final int port){
		if (port < MIN) return LEFT_TO_RANGE;
		if (port > MAX) return RIGHT_TO_RANGE;
		return IN_RANGE;
	}
}
