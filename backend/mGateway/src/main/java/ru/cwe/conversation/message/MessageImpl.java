package ru.cwe.conversation.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.type.MessageType;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
final class MessageImpl implements Message {
	private final boolean response;
	private final UUID uuid;
	private final MessageType type;
	private final String content;
	private final Address from;
	private final Address to;
}
