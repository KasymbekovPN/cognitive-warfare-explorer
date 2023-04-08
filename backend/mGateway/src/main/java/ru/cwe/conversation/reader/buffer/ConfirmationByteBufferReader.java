package ru.cwe.conversation.reader.buffer;

import io.netty.buffer.ByteBuf;
import ru.cwe.conversation.reader.value.ByteBufferValueReader;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.confirmation.ConfirmationMessageBuilder;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;
import ru.cwe.conversation.reader.value.ConfirmationHeaderByteBufferValueReader;
import ru.cwe.conversation.reader.value.StringByteBufferValueReader;
import ru.cwe.conversation.reader.value.UuidByteBufferValueReader;

import java.util.Optional;
import java.util.UUID;

public final class ConfirmationByteBufferReader extends BaseByteBufferReader<ConfirmationMessage> {
	private final ByteBufferValueReader<Integer[]> headerReader;
	private final ByteBufferValueReader<UUID> uuidReader;
	private final ByteBufferValueReader<String> payloadMessageTypeReader;

	public static Builder builder(){
		return new Builder();
	}

	public static ConfirmationByteBufferReader instance(){
		return builder().build();
	}

	private ConfirmationByteBufferReader(ByteBufferValueReader<Integer[]> headerReader,
										ByteBufferValueReader<UUID> uuidReader,
										ByteBufferValueReader<String> payloadMessageTypeReader) {
		this.headerReader = headerReader;
		this.uuidReader = uuidReader;
		this.payloadMessageTypeReader = payloadMessageTypeReader;
	}

	@Override
	protected Optional<ConfirmationMessage> readUnsafe(ByteBuf buffer) {
		Integer[] header = headerReader.read(buffer);
		MessageType messageType = MessageType.valueOf(header[2]);
		ConfirmationResult result = ConfirmationResult.valueOf(header[3]);

		if (messageType.equals(MessageType.CONFIRMATION)){
			ConfirmationMessage message = ConfirmationMessageBuilder.builder()
				.version(header[0])
				.priority(header[1])
				.uuid(uuidReader.read(buffer))
				.result(result)
				.payloadMessageType(payloadMessageTypeReader.read(buffer))
				.build();
			return Optional.of(message);
		}
		return Optional.empty();
	}

	public static class Builder {
		private ByteBufferValueReader<Integer[]> header;
		private ByteBufferValueReader<UUID> uuid;
		private ByteBufferValueReader<String> string;

		public Builder header(ByteBufferValueReader<Integer[]> header){
			this.header = header;
			return this;
		}

		public Builder uuid(ByteBufferValueReader<UUID> uuid){
			this.uuid = uuid;
			return this;
		}

		public Builder string(ByteBufferValueReader<String> string){
			this.string = string;
			return this;
		}

		public ConfirmationByteBufferReader build(){
			return new ConfirmationByteBufferReader(
				header != null ? header : new ConfirmationHeaderByteBufferValueReader(),
				uuid != null ? uuid : new UuidByteBufferValueReader(),
				string != null ? string : StringByteBufferValueReader.instance()
			);
		}
	}
}
