package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;
import ru.cwe.conversation.exception.AbsentFieldRuntimeExceptionBuilderImpl;
import ru.cwe.conversation.message.Priorities;
import ru.cwe.conversation.message.Versions;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;
import ru.cwe.utils.delimiter.FirstDelimiter;

import java.util.Optional;
import java.util.function.Function;

public final class ConfirmationHeaderByteBufferValueReader implements ByteBufferValueReader<Integer[]>{
	private final ExceptionBuilder exceptionBuilder = new ExceptionBuilder(HeaderReadingException::new);

	@Override
	public Integer[] read(final ByteBuf buffer) {
		char header0 = buffer.readChar();
		char header1 = buffer.readChar();

		int version = header0 & Versions.MAX;
		int priority = (header0 >> 10) & Priorities.MAX;
		int messageType = header1 & 0b11;
		int result = (header1 >> 2) & 0b11;

		Optional<RuntimeException> maybeException = exceptionBuilder
			.checkResult(result)
			.build();
		if (maybeException.isPresent()){
			throw maybeException.get();
		}

		Integer[] headers = new Integer[4];
		headers[0] = version;
		headers[1] = priority;
		headers[2] = messageType;
		headers[3] = result;

		return headers;
	}

	private static class ExceptionBuilder extends AbsentFieldRuntimeExceptionBuilderImpl{
		public ExceptionBuilder(final Function<String, RuntimeException> creator) {
			super(creator);
			this.delimiter = new FirstDelimiter("", "; ");
		}

		public ExceptionBuilder checkResult(final int value){
			if (!ConfirmationResult.check(value)){
				appendPartDelimiterAndGet()
					.append("result has invalid value (")
					.append(value)
					.append(") must be in range [")
					.append(ConfirmationResult.INVALID.getValue())
					.append("...")
					.append(ConfirmationResult.RESPONSE.getValue())
					.append("]");
			}
			return this;
		}
	}
}
