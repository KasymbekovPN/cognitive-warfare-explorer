package ru.cwe.conversation.writer.value;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.address.Address;

@RequiredArgsConstructor
public final class AddressByteBufferValueWriter implements ByteBufferValueWriter<Address> {
	private final ByteBufferValueWriter<String> hostWriter;

	@Override
	public void write(ByteBuf buffer, Address element) {
		hostWriter.write(buffer, element.getHost());
		buffer.writeInt(element.getPort());
	}
}
