package ru.cwe.bus.balancer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.tube.TubeOld;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AdaptiveBalancerTest {

	@Test
	void shouldCheckBuilding_ifTubesEmpty() {
		Throwable throwable = catchThrowable(() -> {
			AdaptiveBalancer.builder().build();
		});

		assertThat(throwable)
			.isInstanceOf(BalancerBuilderException.class)
			.hasMessage("No one tube");
	}

	@Test
	void shouldCheckBalance() {
		TestTubeOld tt0 = new TestTubeOld(0);
		TestTubeOld tt1 = new TestTubeOld(7);
		TestTubeOld tt2 = new TestTubeOld(11);

		AdaptiveBalancer balancer = AdaptiveBalancer.builder()
			.tube(tt0)
			.tube(tt1)
			.tube(tt2)
			.build();

		for (int i = 0; i < 30; i++) {
			balancer.balance(createMessage());
		}

		assertThat(tt0.getMessages().size()).isEqualTo(16);
		assertThat(tt1.getMessages().size()).isEqualTo(9);
		assertThat(tt2.getMessages().size()).isEqualTo(5);
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

	@RequiredArgsConstructor
	private static class TestTubeOld implements TubeOld {
		@Getter
		private final List<PayloadMessage> messages = new ArrayList<>();
		private final int offset;

		@Override
		public void send(PayloadMessage message) {
			this.messages.add(message);
		}

		@Override
		public int size() { return messages.size() + offset; }
	}
}
