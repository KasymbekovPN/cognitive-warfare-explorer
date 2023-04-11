package ru.cwe.conversation.reader.buffer;

import io.netty.buffer.ByteBuf;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.reader.value.*;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.message.payload.PayloadMessageBuilder;

import java.util.Optional;
import java.util.UUID;

public final class PayloadByteBufferReader extends BaseByteBufferReader<PayloadMessage> {
	private final ByteBufferValueReader<Integer[]> headerReader;
	private final ByteBufferValueReader<UUID> uuidReader;
	private final ByteBufferValueReader<String> stringReader;
	private final ByteBufferValueReader<Address> addressReader;

	public static Builder builder(){
		return new Builder();
	}

	public static PayloadByteBufferReader instance(){
		return builder().build();
	}

	private PayloadByteBufferReader(final ByteBufferValueReader<Integer[]> headerReader,
									final ByteBufferValueReader<UUID> uuidReader,
									final ByteBufferValueReader<String> stringReader,
									final ByteBufferValueReader<Address> addressReader) {
		this.headerReader = headerReader;
		this.uuidReader = uuidReader;
		this.stringReader = stringReader;
		this.addressReader = addressReader;
	}

	@Override
	protected Optional<PayloadMessage> readUnsafe(final ByteBuf buffer) {
		Integer[] headers = headerReader.read(buffer);
		MessageType messageType = MessageType.valueOf(headers[2]);
		if (messageType.equals(MessageType.REQUEST) || messageType.equals(MessageType.RESPONSE)){
			return Optional.of(
				PayloadMessageBuilder.builder()
					.version(headers[0])
					.priority(headers[1])
					.type(messageType)
					.uuid(uuidReader.read(buffer))
					.contentType(stringReader.read(buffer))
					.content(stringReader.read(buffer))
					.from(addressReader.read(buffer))
					.to(addressReader.read(buffer))
					.build()
			);
		}
		return Optional.empty();
	}

	public static class Builder {
		private ByteBufferValueReader<Integer[]> header;
		private ByteBufferValueReader<UUID> uuid;
		private ByteBufferValueReader<String> string;
		private ByteBufferValueReader<Address> address;

		public Builder header(final ByteBufferValueReader<Integer[]> header){
			this.header = header;
			return this;
		}

		public Builder uuid(final ByteBufferValueReader<UUID> uuid){
			this.uuid = uuid;
			return this;
		}

		public Builder string(final ByteBufferValueReader<String> string){
			this.string = string;
			return this;
		}

		public Builder address(final ByteBufferValueReader<Address> address){
			this.address = address;
			return this;
		}

		public PayloadByteBufferReader build(){
			return new PayloadByteBufferReader(
				header != null ? header : new PayloadHeaderByteBufferValueReader(),
				uuid != null ? uuid : new UuidByteBufferValueReader(),
				string != null ? string : StringByteBufferValueReader.instance(),
				address != null ? address : AddressByteBufferValueReader.instance()
			);
		}
	}
}
