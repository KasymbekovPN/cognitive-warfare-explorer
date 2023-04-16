package ru.cwe.conversation.holder;

import ru.cwe.conversation.message.payload.PayloadMessage;

import java.util.Optional;
import java.util.UUID;

public interface SentMessageHolder {
	boolean offer(PayloadMessage message);
	boolean erase(UUID uuid);
	Optional<PayloadMessage> poll();
}
