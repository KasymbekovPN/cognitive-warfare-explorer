package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;
import ru.cwe.conversation.address.Address;

public final class AddressByteBufferValueWriter implements ByteBufferValueWriter<Address> {
	private final ByteBufferValueWriter<String> hostWriter;

	public static Builder builder(){
		return new Builder();
	}

	public static AddressByteBufferValueWriter instance(){
		return builder().build();
	}

	private AddressByteBufferValueWriter(ByteBufferValueWriter<String> hostWriter) {
		this.hostWriter = hostWriter;
	}

	@Override
	public void write(ByteBuf buffer, Address element) {
		hostWriter.write(buffer, element.getHost());
		buffer.writeInt(element.getPort());
	}

	public static class Builder {
		private ByteBufferValueWriter<String> writer;

		public Builder string(ByteBufferValueWriter<String> writer){
			this.writer = writer;
			return this;
		}

		public AddressByteBufferValueWriter build(){
			return new AddressByteBufferValueWriter(
				writer != null ? writer : StringByteBufferValueWriter.instance()
			);
		}
	}
}
