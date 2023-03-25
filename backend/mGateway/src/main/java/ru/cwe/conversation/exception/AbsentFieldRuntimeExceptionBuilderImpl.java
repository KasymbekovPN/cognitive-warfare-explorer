package ru.cwe.conversation.exception;

import lombok.RequiredArgsConstructor;
import ru.cwe.utils.delimiter.Delimiter;
import ru.cwe.utils.delimiter.FirstDelimiter;

import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public final class AbsentFieldRuntimeExceptionBuilderImpl implements AbsentFieldRuntimeExceptionBuilder {
	private final StringBuilder messageSB = new StringBuilder();
	private final Delimiter delimiter = new FirstDelimiter("Absent fields: ", " & ");
	private final Function<String, RuntimeException> creator;

	@Override
	public AbsentFieldRuntimeExceptionBuilder checkField(String name, Object value) {
		if (value == null){
			messageSB.append(delimiter.next()).append(name);
		}
		return this;
	}

	@Override
	public Optional<RuntimeException> build() {
		return messageSB.isEmpty()
			? Optional.empty()
			: Optional.of(creator.apply(messageSB.toString()));
	}
}
