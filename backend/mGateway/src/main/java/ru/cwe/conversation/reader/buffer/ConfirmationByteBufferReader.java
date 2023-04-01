package ru.cwe.conversation.reader.buffer;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.reader.value.ByteBufferValueReader;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.confirmation.ConfirmationMessageBuilder;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public final class ConfirmationByteBufferReader extends BaseByteBufferReader<ConfirmationMessage> {
	private final ByteBufferValueReader<Integer[]> headerReader;
	private final ByteBufferValueReader<UUID> uuidReader;
	private final ByteBufferValueReader<String> payloadMessageTypeReader;

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
}
