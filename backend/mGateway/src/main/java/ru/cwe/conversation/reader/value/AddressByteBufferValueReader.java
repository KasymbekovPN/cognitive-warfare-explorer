package ru.cwe.conversation.reader.value;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.address.AddressBuilder;

@RequiredArgsConstructor
public final class AddressByteBufferValueReader implements ByteBufferValueReader<Address> {
	private final ByteBufferValueReader<String> stringReader;
	private final AddressBuilder builder;

	@Override
	public Address read(ByteBuf buffer) {
		String host = stringReader.read(buffer);
		int port = buffer.readInt();

		return builder.host(host).port(port).build();
	}
}
