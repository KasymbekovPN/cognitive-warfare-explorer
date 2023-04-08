package ru.cwe.conversation.writer.buffer;

import io.netty.buffer.ByteBuf;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.writer.value.*;

import java.util.UUID;

public final class PayloadByteBufferWriter implements ByteBufferWriter<PayloadMessage> {
	private final ByteBufferValueWriter<Integer[]> headerWriter;
	private final ByteBufferValueWriter<UUID> uuidWriter;
	private final ByteBufferValueWriter<String> stringWriter;
	private final ByteBufferValueWriter<Address> addressWriter;

	public static Builder builder(){
		return new Builder();
	}

	public static PayloadByteBufferWriter instance(){
		return builder().build();
	}

	private PayloadByteBufferWriter(ByteBufferValueWriter<Integer[]> headerWriter,
								    ByteBufferValueWriter<UUID> uuidWriter,
								    ByteBufferValueWriter<String> stringWriter,
								    ByteBufferValueWriter<Address> addressWriter) {
		this.headerWriter = headerWriter;
		this.uuidWriter = uuidWriter;
		this.stringWriter = stringWriter;
		this.addressWriter = addressWriter;
	}

	@Override
	public void write(ByteBuf buffer, PayloadMessage element) {
		Integer[] headers = new Integer[4];
		headers[0] = element.getVersion();
		headers[1] = element.getPriority();
		headers[2] = element.getType().getValue();
		headerWriter.write(buffer, headers);
		uuidWriter.write(buffer, element.getUuid());
		stringWriter.write(buffer, element.getContentType());
		stringWriter.write(buffer, element.getContent());
		addressWriter.write(buffer, element.getFrom());
		addressWriter.write(buffer, element.getTo());
	}

	public static class Builder {
		private ByteBufferValueWriter<Integer[]> headerWriter;
		private ByteBufferValueWriter<UUID> uuidWriter;
		private ByteBufferValueWriter<String> stringWriter;
		private ByteBufferValueWriter<Address> addressWriter;

		public Builder header(ByteBufferValueWriter<Integer[]> headerWriter){
			this.headerWriter = headerWriter;
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

		public Builder address(ByteBufferValueWriter<Address> addressWriter){
			this.addressWriter = addressWriter;
			return this;
		}

		public PayloadByteBufferWriter build(){
			return new PayloadByteBufferWriter(
				headerWriter != null ? headerWriter : new PayloadHeaderByteBufferValueWriter(),
				uuidWriter != null ? uuidWriter : new UuidByteBufferValueWriter(),
				stringWriter != null ? stringWriter : StringByteBufferValueWriter.instance(),
				addressWriter != null ? addressWriter : AddressByteBufferValueWriter.instance()
			);
		}
	}
}
