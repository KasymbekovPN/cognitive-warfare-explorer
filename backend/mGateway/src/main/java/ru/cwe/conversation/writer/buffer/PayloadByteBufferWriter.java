package ru.cwe.conversation.writer.buffer;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.writer.value.ByteBufferValueWriter;

import java.util.UUID;

@RequiredArgsConstructor
public final class PayloadByteBufferWriter implements ByteBufferWriter<PayloadMessage> {
	private final ByteBufferValueWriter<Integer[]> headerWriter;
	private final ByteBufferValueWriter<UUID> uuidWriter;
	private final ByteBufferValueWriter<String> stringWriter;
	private final ByteBufferValueWriter<Address> addressWriter;

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
}
