package ru.cwe.conversation.writer.buffer;

import io.netty.buffer.ByteBuf;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.writer.value.ByteBufferValueWriter;
import ru.cwe.conversation.writer.value.ConfirmationHeaderByteBufferValueWriter;
import ru.cwe.conversation.writer.value.StringByteBufferValueWriter;
import ru.cwe.conversation.writer.value.UuidByteBufferValueWriter;

import java.util.UUID;

public final class ConfirmationByteBufferWriter implements ByteBufferWriter<ConfirmationMessage> {
	private final ByteBufferValueWriter<Integer[]> headersWriter;
	private final ByteBufferValueWriter<UUID> uuidWriter;
	private final ByteBufferValueWriter<String> stringWriter;

	public static Builder builder(){
		return new Builder();
	}

	public static ConfirmationByteBufferWriter instance(){
		return builder().build();
	}

	private ConfirmationByteBufferWriter(ByteBufferValueWriter<Integer[]> headersWriter,
										 ByteBufferValueWriter<UUID> uuidWriter,
										 ByteBufferValueWriter<String> stringWriter) {
		this.headersWriter = headersWriter;
		this.uuidWriter = uuidWriter;
		this.stringWriter = stringWriter;
	}

	@Override
	public void write(ByteBuf buffer, ConfirmationMessage element) {
		Integer[] headers = new Integer[4];
		headers[0] = element.getVersion();
		headers[1] = element.getPriority();
		headers[2] = element.getType().getValue();
		headers[3] = element.getResult().getValue();
		headersWriter.write(buffer, headers);
		uuidWriter.write(buffer, element.getUuid());
		stringWriter.write(buffer, element.getPayloadMessageType());
	}

	public static class Builder {
		private ByteBufferValueWriter<Integer[]> headersWriter;
		private ByteBufferValueWriter<UUID> uuidWriter;
		private ByteBufferValueWriter<String> stringWriter;

		public Builder header(ByteBufferValueWriter<Integer[]> headersWriter){
			this.headersWriter = headersWriter;
			return this;
		}

		public Builder uuid(ByteBufferValueWriter<UUID> uuidWriter){
			this.uuidWriter = uuidWriter;
			return this;
		}

		public Builder string(ByteBufferValueWriter<String> stringWriter){
			this.stringWriter = stringWriter;
			return this;
		}

		public ConfirmationByteBufferWriter build(){
			return new ConfirmationByteBufferWriter(
				headersWriter != null ? headersWriter : new ConfirmationHeaderByteBufferValueWriter(),
				uuidWriter != null ? uuidWriter : new UuidByteBufferValueWriter(),
				stringWriter != null ? stringWriter : StringByteBufferValueWriter.instance()
			);
		}
	}
}
