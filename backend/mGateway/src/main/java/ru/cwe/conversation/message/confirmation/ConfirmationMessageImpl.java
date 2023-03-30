package ru.cwe.conversation.message.confirmation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.message.MessageType;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
final class ConfirmationMessageImpl implements ConfirmationMessage {
	private final int version;
	private final MessageType type = MessageType.CONFIRMATION;
	private final UUID uuid;
	private final ConfirmationResult result;
	private final String payloadMessageType;
}
