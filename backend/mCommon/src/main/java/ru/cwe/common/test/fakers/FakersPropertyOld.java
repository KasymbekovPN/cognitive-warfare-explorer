package ru.cwe.common.test.fakers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// TODO: 18.06.2023 del
@RequiredArgsConstructor
public enum FakersPropertyOld {
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
