package ru.cwe.conversation.gateway.in;

import io.netty.channel.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import utils.TestChannelFuture;

import static org.assertj.core.api.Assertions.assertThat;

class InGatewayImplTest {

	@Test
	void shouldCheckShutdown() {
		TestServerHolder holder = new TestServerHolder(false, null);
		InGatewayImpl inGateway = new InGatewayImpl(
			holder,
			new TestFutureProcessor()
		);

		inGateway.shutdown();
		assertThat(holder.isShutdown()).isTrue();
	}

	@Test
	void shouldCheckShutdown_onStart() {
		TestServerHolder holder = new TestServerHolder(true, null);
		InGatewayImpl inGateway = new InGatewayImpl(
			holder,
			new TestFutureProcessor()
		);

		inGateway.start();
		assertThat(holder.isShutdown()).isTrue();
	}

	@Test
	void shouldCheckStart() {
		TestChannelFuture expectedFuture = new TestChannelFuture();
		TestServerHolder holder = new TestServerHolder(false, expectedFuture);
		TestFutureProcessor futureProcessor = new TestFutureProcessor();
		InGatewayImpl inGateway = new InGatewayImpl(
			holder,
			futureProcessor
		);

		inGateway.start();
		assertThat(futureProcessor.getChannelFuture()).isEqualTo(expectedFuture);
	}

	@RequiredArgsConstructor
	private static class TestServerHolder implements ServerHolder{
		private final boolean doThrow;
		private final ChannelFuture channelFuture;

		@Getter
		private boolean shutdown;

		@Override
		public void shutdown() {
			this.shutdown = true;
		}

		@Override
		public ChannelFuture getFuture() {
			if (doThrow){
				throw new RuntimeException("");
			}
			return channelFuture;
		}
	}

	private static class TestFutureProcessor implements FutureProcessor{
		@Getter
		private ChannelFuture channelFuture;

		@Override
		public void process(ChannelFuture future) throws InterruptedException {
			this.channelFuture = future;
		}
	}
}
