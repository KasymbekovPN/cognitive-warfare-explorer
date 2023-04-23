package ru.cwe.common.test.message;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.cwe.common.message.MessageType;
import ru.cwe.common.message.confirmation.ConfirmationMessage;
import ru.cwe.common.message.confirmation.ConfirmationResult;

import java.util.UUID;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class TestConfirmationMessage implements ConfirmationMessage {
	private final int version;
	private final int priority;
	private final UUID uuid;
	private final MessageType type = MessageType.CONFIRMATION;
	private final ConfirmationResult result;
	private final String payloadMessageType;
}
