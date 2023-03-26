package utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public final class TestConfirmationMessage implements ConfirmationMessage {
	private final UUID uuid;
	private final MessageType type = MessageType.CONFIRMATION;
	private final ConfirmationResult result;
	private final String payloadMessageType;
}
