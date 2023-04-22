package ru.cwe.conversation.tube;

import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.tube.datum.TubeDatum;
import ru.cwe.conversation.tube.datum.TubeDatumImpl;
import ru.cwe.conversation.tube.listener.TubeListener;
import ru.cwe.common.reflection.Reflections;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import static org.assertj.core.api.Assertions.assertThat;

class TubeImplTest {

	@SneakyThrows
	@Test
	void shouldCheckPuttingMessage() {
		int initCapacity = 10;
		int amount = 5;
		ArrayBlockingQueue<TubeDatum> queue = new ArrayBlockingQueue<>(initCapacity);
		TubeImpl tube = TubeImpl.builder()
			.queue(queue)
			.build();

		ArrayList<PayloadMessage> expectedMessages = new ArrayList<>();
		for (int i = 0; i < amount; i++) {
			PayloadMessage message = createMessage();
			expectedMessages.add(message);
			boolean result = tube.put(message);
			assertThat(result).isTrue();
		}

		assertThat(queue.size()).isEqualTo(amount);
		for (PayloadMessage expectedMessage : expectedMessages) {
			TubeDatum datum = queue.take();
			assertThat(datum.getMessage()).isEqualTo(expectedMessage);
		}
	}

	@SneakyThrows
	@Test
	void shouldCheckPuttingDatum() {
		int initCapacity = 10;
		int amount = 5;
		ArrayBlockingQueue<TubeDatum> queue = new ArrayBlockingQueue<>(initCapacity);
		TubeImpl tube = TubeImpl.builder()
			.queue(queue)
			.build();

		ArrayList<PayloadMessage> expectedMessages = new ArrayList<>();
		for (int i = 0; i < amount; i++) {
			PayloadMessage message = createMessage();
			expectedMessages.add(message);
			boolean result = tube.put(new TubeDatumImpl(message));
			assertThat(result).isTrue();
		}

		assertThat(queue.size()).isEqualTo(amount);
		for (PayloadMessage expectedMessage : expectedMessages) {
			TubeDatum datum = queue.take();
			assertThat(datum.getMessage()).isEqualTo(expectedMessage);
		}
	}

	@Test
	void shouldCheckListeners_onPutting() {
		TestUuidListener testUuidListener = new TestUuidListener();
		TestPriorityListener testPriorityListener = new TestPriorityListener();
		TubeImpl tube = TubeImpl.builder()
			.puttingListener(testUuidListener)
			.puttingListener(testPriorityListener)
			.build();

		HashSet<UUID> expectedUuids = new HashSet<>();
		HashSet<Integer> expectedPriorities = new HashSet<>();
		int amount = 5;
		for (int i = 0; i < amount; i++) {
			UUID uuid = Fakers.base().uuid().uuid();
			expectedUuids.add(uuid);
			int priority = Fakers.message().priority();
			expectedPriorities.add(priority);
			PayloadMessage message = createMessage(uuid, priority);
			tube.put(message);
		}

		assertThat(testUuidListener.getUuids()).isEqualTo(expectedUuids);
		assertThat(testPriorityListener.getPriorities()).isEqualTo(expectedPriorities);
	}

	@SneakyThrows
	@Test
	void shouldCheckTaking() {
		List<Integer> expectedPriorities = List.of(5, 4, 3, 2, 1);
		TubeImpl tube = TubeImpl.instance();

		for (Integer priority : expectedPriorities) {
			tube.put(createMessage(priority));
		}

		ArrayList<Integer> priorities = new ArrayList<>();
		for (int i = 0; i < expectedPriorities.size(); i++) {
			priorities.add(tube.take().getMessage().getPriority());
		}

		assertThat(priorities).isEqualTo(expectedPriorities);
	}

	@SneakyThrows
	@Test
	void shouldCheckListeners_onTaking() {
		TestUuidListener testUuidListener = new TestUuidListener();
		TestPriorityListener testPriorityListener = new TestPriorityListener();
		TubeImpl tube = TubeImpl.builder()
			.takingListener(testUuidListener)
			.takingListener(testPriorityListener)
			.build();

		HashSet<UUID> expectedUuids = new HashSet<>();
		HashSet<Integer> expectedPriorities = new HashSet<>();
		int amount = 5;
		for (int i = 0; i < amount; i++) {
			UUID uuid = Fakers.base().uuid().uuid();
			expectedUuids.add(uuid);
			int priority = Fakers.message().priority();
			expectedPriorities.add(priority);
			PayloadMessage message = createMessage(uuid, priority);
			tube.put(message);
		}

		for (int i = 0; i < amount; i++) {
			tube.take();
		}

		assertThat(testUuidListener.getUuids()).isEqualTo(expectedUuids);
		assertThat(testPriorityListener.getPriorities()).isEqualTo(expectedPriorities);
	}

	@SneakyThrows
	@Test
	void shouldCheckPuttingListenersAddition() {
		List<TubeListener> expectedListeners = List.of(
			new TestUuidListener(),
			new TestPriorityListener(),
			new TestUuidListener()
		);
		TubeImpl tube = TubeImpl.instance();

		for (TubeListener listener : expectedListeners) {
			tube.addPuttingListener(listener);
		}

		assertThat(Reflections.get(tube, "puttingListeners")).isEqualTo(expectedListeners);
	}

	@SneakyThrows
	@Test
	void shouldCheckPuttingListenersErasing() {
		List<TubeListener> expectedListeners = List.of(
			new TestUuidListener(),
			new TestPriorityListener(),
			new TestUuidListener()
		);
		TubeImpl tube = TubeImpl.instance();

		for (TubeListener listener : expectedListeners) {
			tube.addPuttingListener(listener);
		}

		for (TubeListener expectedListener : expectedListeners) {
			tube.erasePuttingListener(expectedListener);
		}

		assertThat(Reflections.get(tube, "puttingListeners")).isEqualTo(List.of());
	}

	@SneakyThrows
	@Test
	void shouldCheckTakingListenersAddition() {
		List<TubeListener> expectedListeners = List.of(
			new TestUuidListener(),
			new TestPriorityListener(),
			new TestUuidListener()
		);
		TubeImpl tube = TubeImpl.instance();

		for (TubeListener listener : expectedListeners) {
			tube.addTakingListener(listener);
		}

		assertThat(Reflections.get(tube, "takingListeners")).isEqualTo(expectedListeners);
	}

	@SneakyThrows
	@Test
	void shouldCheckTakingListenersErasing() {
		List<TubeListener> expectedListeners = List.of(
			new TestUuidListener(),
			new TestPriorityListener(),
			new TestUuidListener()
		);
		TubeImpl tube = TubeImpl.instance();

		for (TubeListener listener : expectedListeners) {
			tube.addTakingListener(listener);
		}

		for (TubeListener expectedListener : expectedListeners) {
			tube.eraseTakingListener(expectedListener);
		}

		assertThat(Reflections.get(tube, "takingListeners")).isEqualTo(List.of());
	}

	// TODO: 22.04.2023 move to Fakers
	private PayloadMessage createMessage(){
		return new TestPayloadMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			MessageType.REQUEST,
			Fakers.base().uuid().uuid(),
			Fakers.base().string().string(),
			Fakers.base().string().string(),
			Fakers.address().address(),
			Fakers.address().address()
		);
	}

	// TODO: 22.04.2023 move into Fakers
	private PayloadMessage createMessage(UUID uuid, int priority){
		return new TestPayloadMessage(
			Fakers.message().version(),
			priority,
			MessageType.REQUEST,
			uuid,
			Fakers.base().string().string(),
			Fakers.base().string().string(),
			Fakers.address().address(),
			Fakers.address().address()
		);
	}

	// TODO: 22.04.2023 move into Fakers
	private PayloadMessage createMessage(int priority){
		return new TestPayloadMessage(
			Fakers.message().version(),
			priority,
			MessageType.REQUEST,
			Fakers.base().uuid().uuid(),
			Fakers.base().string().string(),
			Fakers.base().string().string(),
			Fakers.address().address(),
			Fakers.address().address()
		);
	}

	private static class TestUuidListener implements TubeListener {
		@Getter
		private final Set<UUID> uuids = new HashSet<>();

		@Override
		public void listen(TubeDatum datum) {
			this.uuids.add(datum.getMessage().getUuid());
		}
	}

	private static class TestPriorityListener implements TubeListener {
		@Getter
		private final Set<Integer> priorities = new HashSet<>();

		@Override
		public void listen(TubeDatum datum) {
			this.priorities.add(datum.getMessage().getPriority());
		}
	}
}
