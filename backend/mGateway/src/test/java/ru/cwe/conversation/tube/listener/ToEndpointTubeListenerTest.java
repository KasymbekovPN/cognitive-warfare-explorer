package ru.cwe.conversation.tube.listener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.tube.datum.TubeDatumImpl;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ToEndpointTubeListenerTest {

	@Test
	void shouldCheckInitialCountersGetting() {
		ToEndpointTubeListener listener = new ToEndpointTubeListener();

		assertThat(listener.getCounters()).isEmpty();
	}

	@SneakyThrows
	@RepeatedTest(1_000)
	void shouldCheckCountersGetting() {
		ToEndpointTubeListener listener = new ToEndpointTubeListener();

		List<Address> toAddresses = createAddresses(10);

		int threadAmount = 10;
		ArrayList<TestThread> threads = new ArrayList<>();
		for (int i = 0; i < threadAmount; i++) {
			threads.add(new TestThread(toAddresses, listener));
		}

		for (Thread thread : threads) {
			thread.start();
		}
		Thread.sleep(10);

		HashMap<Address, Integer> expectedMap = new HashMap<>();
		for (TestThread thread : threads) {
			for (Map.Entry<Address, Integer> entry : thread.getResult().entrySet()) {
				Address address = entry.getKey();
				Integer value = expectedMap.getOrDefault(address, 0);
				expectedMap.put(address, value + entry.getValue());
			}
		}

		assertThat(listener.getCounters()).isEqualTo(expectedMap);
	}

	@RepeatedTest(1_000)
	void shouldCheckInitialCounterGetting() {
		ToEndpointTubeListener listener = new ToEndpointTubeListener();
		Integer counter = listener.getCounter(Fakers.address().address());

		assertThat(counter).isZero();
	}

	@SneakyThrows
	@RepeatedTest(1_000)
	void shouldCheckCounterGetting() {
		ToEndpointTubeListener listener = new ToEndpointTubeListener();

		List<Address> toAddresses = createAddresses(10);

		int threadAmount = 10;
		ArrayList<TestThread> threads = new ArrayList<>();
		for (int i = 0; i < threadAmount; i++) {
			threads.add(new TestThread(toAddresses, listener));
		}

		for (Thread thread : threads) {
			thread.start();
		}
		Thread.sleep(10);

		HashMap<Address, Integer> expectedMap = new HashMap<>();
		for (TestThread thread : threads) {
			for (Map.Entry<Address, Integer> entry : thread.getResult().entrySet()) {
				Address address = entry.getKey();
				Integer value = expectedMap.getOrDefault(address, 0);
				expectedMap.put(address, value + entry.getValue());
			}
		}

		for (Map.Entry<Address, Integer> entry : expectedMap.entrySet()) {
			assertThat(listener.getCounter(entry.getKey())).isEqualTo(entry.getValue());
		}
	}

	// TODO: 19.04.2023 move to Fakers
	private List<Address> createAddresses(int amount){
		ArrayList<Address> toAddresses = new ArrayList<>();
		for (int i = 0; i < amount; i++) {
			do {
				Address address = Fakers.address().address();
				if (!toAddresses.contains(address)){
					toAddresses.add(address);
				}
			} while (toAddresses.size() == i + 1);
		}

		return toAddresses;
	}

	// TODO: 19.04.2023 move to Fakers
	private static PayloadMessage createMessage(Address toAddress){
		return new TestPayloadMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			MessageType.REQUEST,
			Fakers.base().uuid().uuid(),
			Fakers.base().string().string(),
			Fakers.base().string().string(),
			Fakers.address().address(),
			toAddress
		);
	}

	@RequiredArgsConstructor
	private static class TestThread extends Thread {
		private final List<Address> toAddresses;
		private final ToEndpointTubeListener listener;
		@Getter
		private final Map<Address, Integer> result = new HashMap<>();

		@Override
		public void run() {
			for (Address toAddress : toAddresses) {
				PayloadMessage message = createMessage(toAddress);
				int amount = Fakers.base().number().between(1, 10);
				for (int y = 0; y < amount; y++) {
					listener.listen(new TubeDatumImpl(message));
				}
				result.put(toAddress, amount);
			}
		}
	}
}
