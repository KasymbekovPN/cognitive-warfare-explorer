package ru.cwe.common.test.message;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.cwe.common.address.Address;
import ru.cwe.common.message.MessageType;
import ru.cwe.common.message.payload.PayloadMessage;

import java.util.UUID;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public final class TestPayloadMessage implements PayloadMessage {
	private final int version;
	private final int priority;
	private final MessageType type;
	private final UUID uuid;
	private final String contentType;
	private final String content;
	private final Address from;
	private final Address to;
}
