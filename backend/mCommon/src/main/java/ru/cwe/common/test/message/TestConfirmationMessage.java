package ru.cwe.common.test.message;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import ru.cwe.common.message.MessageType;
import ru.cwe.common.message.confirmation.ConfirmationMessage;
import ru.cwe.common.message.confirmation.ConfirmationResult;

import java.util.UUID;

// TODO: 22.04.2023 add test
@RequiredArgsConstructor
@EqualsAndHashCode
public class TestConfirmationMessage implements ConfirmationMessage {
	private final int version;
	private final int priority;
	private final UUID uuid;
	private final MessageType type = MessageType.CONFIRMATION;
	private final ConfirmationResult result;
	private final String payloadMessageType;

	@Override
	public int getVersion() {
		return -1;
	}

	@Override
	public int getPriority() {
		return -1;
	}

	@Override
	public MessageType getType() {
		return null;
	}

	@Override
	public UUID getUuid() {
		return null;
	}

	@Override
	public ConfirmationResult getResult() {
		return null;
	}

	@Override
	public String getPayloadMessageType() {
		return null;
	}
}
