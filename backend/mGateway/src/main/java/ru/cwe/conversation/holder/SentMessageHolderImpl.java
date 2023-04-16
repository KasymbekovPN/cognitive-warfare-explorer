package ru.cwe.conversation.holder;

import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.message.payload.PayloadMessage;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RequiredArgsConstructor
public final class SentMessageHolderImpl implements SentMessageHolder {
	private final Map<UUID, PayloadMessage> messages = new HashMap<>();
	private final Queue<UUID> uuids = new ArrayDeque<>();
	private final int size;

	@Override
	public boolean offer(final PayloadMessage message) {
		boolean result = false;
		synchronized (this){
			if (size > uuids.size()){
				UUID uuid = message.getUuid();
				uuids.offer(uuid);
				messages.put(uuid, message);
				result = true;
			}
		}

		return result;
	}

	@Override
	public boolean erase(final UUID uuid) {
		boolean result = false;
		synchronized (this) {
			result = uuids.contains(uuid);
			uuids.remove(uuid);
			messages.remove(uuid);
		}
		return result;
	}

	@Override
	public Optional<PayloadMessage> poll() {
		Optional<PayloadMessage> result = Optional.empty();
		synchronized (this){
			UUID uuid = uuids.poll();
			if (uuid != null){
				result = Optional.of(messages.get(uuid));
				messages.remove(uuid);
			}
		}
		return result;
	}
}
