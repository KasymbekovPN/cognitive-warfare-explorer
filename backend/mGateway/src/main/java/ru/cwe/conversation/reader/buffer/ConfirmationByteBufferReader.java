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
	private final ByteBufferValueReader<MessageType> typeReader;
	private final ByteBufferValueReader<UUID> uuidReader;
	private final ByteBufferValueReader<ConfirmationResult> resultReader;
	private final ByteBufferValueReader<String> payloadMessageTypeReader;

	@Override
	protected Optional<ConfirmationMessage> readUnsafe(ByteBuf buffer) {
		MessageType messageType = typeReader.read(buffer);
		if (messageType.equals(MessageType.CONFIRMATION)){
			ConfirmationMessage message = ConfirmationMessageBuilder.builder()
				.uuid(uuidReader.read(buffer))
				.result(resultReader.read(buffer))
				.payloadMessageType(payloadMessageTypeReader.read(buffer))
				.build();
			return Optional.of(message);
		}
		return Optional.empty();
	}
}
