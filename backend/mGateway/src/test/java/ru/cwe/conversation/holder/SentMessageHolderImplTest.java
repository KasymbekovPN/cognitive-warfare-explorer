package ru.cwe.conversation.holder;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.utils.reflection.Reflections;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class SentMessageHolderImplTest {

	@SneakyThrows
	@Test
	void shouldCheckOffer() {
		SentMessageHolderImpl holder = new SentMessageHolderImpl(10);

		Set<UUID> expectedUuids = new HashSet<>();
		HashMap<UUID, PayloadMessage> expectedMessages = new HashMap<>();
		for (int i = 0; i < 7; i++) {
			UUID uuid = Fakers.base().uuid().uuid();
			PayloadMessage message = createMessage(uuid);
			expectedMessages.put(uuid, message);
			expectedUuids.add(uuid);
			boolean result = holder.offer(message);

			assertThat(result).isTrue();
		}

		assertThat(new HashSet<>((ArrayDeque<UUID>) Reflections.get(holder, "uuids"))).isEqualTo(expectedUuids);
		assertThat(Reflections.get(holder, "messages")).isEqualTo(expectedMessages);
	}

	@SneakyThrows
	@Test
	void shouldCheckOffer_withOverload() {
		SentMessageHolderImpl holder = new SentMessageHolderImpl(0);

		boolean result = holder.offer(createMessage(Fakers.base().uuid().uuid()));
		assertThat(result).isFalse();
		assertThat((ArrayDeque<UUID>) Reflections.get(holder, "uuids")).isEmpty();
		assertThat((Map<UUID, PayloadMessage>) Reflections.get(holder, "messages")).isEmpty();
	}

	@SneakyThrows
	@Test
	void shouldCheckErase_ifEmpty() {
		SentMessageHolderImpl holder = new SentMessageHolderImpl(10);

		boolean result = holder.erase(Fakers.base().uuid().uuid());
		assertThat(result).isFalse();
		assertThat((ArrayDeque<UUID>) Reflections.get(holder, "uuids")).isEmpty();
		assertThat((Map<UUID, PayloadMessage>) Reflections.get(holder, "messages")).isEmpty();
	}

	@SneakyThrows
	@Test
	void shouldCheckErase() {
		SentMessageHolderImpl holder = new SentMessageHolderImpl(10);

		UUID uuid = null;
		int size = 5;
		for (int i = 0; i < size; i++) {
			uuid = Fakers.base().uuid().uuid();
			holder.offer(createMessage(uuid));
		}
		boolean result = holder.erase(uuid);

		assertThat(result).isTrue();
		assertThat(((ArrayDeque<UUID>) Reflections.get(holder, "uuids")).size()).isEqualTo(size - 1);
		assertThat(((Map<UUID, PayloadMessage>) Reflections.get(holder, "messages")).size()).isEqualTo(size - 1);
	}

	@Test
	void shouldCheckPolling_ifEmpty() {
		SentMessageHolderImpl holder = new SentMessageHolderImpl(10);
		assertThat(holder.poll()).isEmpty();
	}

	@Test
	void shouldCheckPolling() {
		SentMessageHolderImpl holder = new SentMessageHolderImpl(10);
		ArrayList<UUID> uuids = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			UUID uuid = Fakers.base().uuid().uuid();
			uuids.add(uuid);
			holder.offer(createMessage(uuid));
		}

		for (UUID uuid : uuids) {
			Optional<PayloadMessage> maybe = holder.poll();
			assertThat(maybe).isPresent();
			assertThat(maybe.get().getUuid()).isEqualTo(uuid);
		}
		assertThat(holder.poll()).isEmpty();
	}

	private PayloadMessage createMessage(UUID uuid) {
		return new TestPayloadMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			MessageType.REQUEST,
			uuid,
			Fakers.base().string().string(),
			Fakers.base().string().string(),
			Fakers.address().address(),
			Fakers.address().address()
		);
	}
}
