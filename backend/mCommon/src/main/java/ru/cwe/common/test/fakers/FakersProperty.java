package ru.cwe.common.test.fakers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FakersProperty {
	TYPE("type"),
	OPERATION("operation"),

	SIMPLE("operation.simple"),
	BETWEEN("operation.between"),
	LESS("operation.less"),
	GREATER("operation.greater"),

	MIN("param.min"),
	MAX("param.max"),
	THRESHOLD("param.threshold");

	@Getter
	private final String value;
}
