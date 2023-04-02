package ru.cwe.conversation.exception;

import lombok.RequiredArgsConstructor;
import ru.cwe.utils.delimiter.Delimiter;
import ru.cwe.utils.delimiter.FirstDelimiter;

import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class AbsentFieldRuntimeExceptionBuilderImpl implements AbsentFieldRuntimeExceptionBuilder {
	protected final StringBuilder messageSB = new StringBuilder();
	private final Function<String, RuntimeException> creator;

	protected Delimiter delimiter = new FirstDelimiter("Absent fields: ", " & ");

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

	protected StringBuilder appendPartDelimiterAndGet(){
		if (!messageSB.isEmpty()){
			messageSB.append("; ");
		}
		return messageSB;
	}
}
