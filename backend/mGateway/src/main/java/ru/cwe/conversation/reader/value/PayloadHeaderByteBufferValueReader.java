package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;
import ru.cwe.conversation.exception.AbsentFieldRuntimeExceptionBuilderImpl;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.Priorities;
import ru.cwe.conversation.message.Versions;
import ru.cwe.utils.delimiter.FirstDelimiter;

import java.util.Optional;
import java.util.function.Function;

public final class PayloadHeaderByteBufferValueReader implements ByteBufferValueReader<Integer[]>{
	private final ExceptionBuilder exceptionBuilder = new ExceptionBuilder(HeaderReadingException::new);

	@Override
	public Integer[] read(ByteBuf buffer) {
		char header0 = buffer.readChar();
		char header1 = buffer.readChar();

		int version = header0 & Versions.MAX;
		int priority = (header0 >> 10) & Priorities.MAX;
		int messageType = header1 & 0b111;

		Optional<RuntimeException> maybeException = exceptionBuilder
			.checkMessageType(messageType)
			.build();
		if (maybeException.isPresent()){
			throw maybeException.get();
		}

		Integer[] headers = new Integer[4];
		headers[0] = version;
		headers[1] = priority;
		headers[2] = messageType;

		return headers;
	}

	private static class ExceptionBuilder extends AbsentFieldRuntimeExceptionBuilderImpl {
		public ExceptionBuilder(Function<String, RuntimeException> creator) {
			super(creator);
			this.delimiter = new FirstDelimiter("", "; ");
		}

		public ExceptionBuilder checkMessageType(int value){
			if (!MessageType.check(value)){
				appendPartDelimiterAndGet()
					.append("type has invalid value (")
					.append(value)
					.append(") must be in range [")
					.append(MessageType.INVALID.getValue())
					.append("...")
					.append(MessageType.CONFIRMATION.getValue())
					.append("]");
			}
			return this;
		}
	}
}
