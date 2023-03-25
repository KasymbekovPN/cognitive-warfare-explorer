package ru.cwe.utils.delimiter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FirstDelimiter implements Delimiter {
	private final String firstDelimiter;
	private final String nextDelimiter;

	private boolean isFirst = true;

	@Override
	public String next() {
		if (isFirst){
			isFirst = false;
			return firstDelimiter;
		}
		return nextDelimiter;
	}
}
