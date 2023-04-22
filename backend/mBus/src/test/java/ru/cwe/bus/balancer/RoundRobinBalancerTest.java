package ru.cwe.bus.balancer;

import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.tube.creator.DatumCreator;
import ru.cwe.conversation.tube.TubeOld;
import ru.cwe.conversation.tube.datum.TubeDatum;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class RoundRobinBalancerTest {

	@Test
	void shouldCheckBuilding_ifTubesEmpty() {
		Throwable throwable = catchThrowable(() -> {
			RoundRobinBalancer.builder().build();
		});

		assertThat(throwable)
			.isInstanceOf(BalancerBuilderException.class)
			.hasMessage("No one tube");
	}

	@Test
	void shouldCheckBalance() {
		TestTubeOld tt0 = new TestTubeOld();
		TestTubeOld tt1 = new TestTubeOld();
		TestTubeOld tt2 = new TestTubeOld();

		RoundRobinBalancer balancer = RoundRobinBalancer.builder()
			.tube(tt0)
			.tube(tt1)
			.tube(tt2)
			.build();

		for (int i = 0; i < 8; i++) {
			balancer.balance(createMessage());
		}

		assertThat(tt0.size()).isEqualTo(3);
		assertThat(tt1.size()).isEqualTo(3);
		assertThat(tt2.size()).isEqualTo(2);
	}

	private PayloadMessage createMessage() {
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

	private static class TestTubeOld implements TubeOld {
		private final List<TubeDatum> messages = new ArrayList<>();

		@Override
		public boolean put(TubeDatum datum) {
			this.messages.add(datum);
			return false;
		}

		@Override
		public void dispose() throws InterruptedException {}

		@Override
		public int size() {
			return messages.size();
		}

		@Override
		public DatumCreator creator() {return null;}
	}
}
