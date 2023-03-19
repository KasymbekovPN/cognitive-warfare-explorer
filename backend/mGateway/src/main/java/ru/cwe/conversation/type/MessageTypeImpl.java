package ru.cwe.conversation.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
final class MessageTypeImpl implements MessageType {
	private final String type;
}
