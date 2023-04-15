package utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.payload.PayloadMessage;

import java.util.UUID;

@EqualsAndHashCode
@RequiredArgsConstructor
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
