package ru.cwe.conversation.writer.buffer;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.writer.value.ByteBufferValueWriter;

import java.util.UUID;

@RequiredArgsConstructor
public final class ConfirmationByteBufferWriter implements ByteBufferWriter<ConfirmationMessage> {
	private final ByteBufferValueWriter<Integer[]> headersWriter;
	private final ByteBufferValueWriter<UUID> uuidWriter;
	private final ByteBufferValueWriter<String> stringWriter;

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
}
