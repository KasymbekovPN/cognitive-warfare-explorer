package utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;

import java.util.UUID;

// TODO: 22.04.2023 del
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public final class TestConfirmationMessage implements ConfirmationMessage {
	private final int version;
	private final int priority;
	private final UUID uuid;
	private final MessageType type = MessageType.CONFIRMATION;
	private final ConfirmationResult result;
	private final String payloadMessageType;
}
