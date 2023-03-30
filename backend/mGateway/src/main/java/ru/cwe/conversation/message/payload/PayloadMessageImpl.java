package ru.cwe.conversation.message.payload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.MessageType;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class PayloadMessageImpl implements PayloadMessage {
	private final int version;
	private final MessageType type;
	private final UUID uuid;
	private final String contentType;
	private final String content;
	private final Address from;
	private final Address to;
}
