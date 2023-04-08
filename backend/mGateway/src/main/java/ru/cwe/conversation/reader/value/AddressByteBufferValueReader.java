package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.address.AddressBuilder;

public final class AddressByteBufferValueReader implements ByteBufferValueReader<Address> {
	private final ByteBufferValueReader<String> stringReader;
	private final AddressBuilder builder;

	public static Builder builder(){
		return new Builder();
	}

	public static AddressByteBufferValueReader instance(){
		return builder().build();
	}

	private AddressByteBufferValueReader(ByteBufferValueReader<String> stringReader, AddressBuilder builder) {
		this.stringReader = stringReader;
		this.builder = builder;
	}

	@Override
	public Address read(ByteBuf buffer) {
		String host = stringReader.read(buffer);
		int port = buffer.readInt();

		return builder.host(host).port(port).build();
	}

	public static class Builder {
		private ByteBufferValueReader<String> stringReader;

		public Builder string(ByteBufferValueReader<String> stringReader){
			this.stringReader = stringReader;
			return this;
		}

		public AddressByteBufferValueReader build(){
			return new AddressByteBufferValueReader(
				stringReader != null ? stringReader : StringByteBufferValueReader.instance(),
				AddressBuilder.builder()
			);
		}
	}
}
