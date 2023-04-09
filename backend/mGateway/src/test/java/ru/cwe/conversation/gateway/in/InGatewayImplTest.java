package ru.cwe.conversation.gateway.in;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.gateway.BootstrapHolder;
import utils.TestChannelFuture;

import static org.assertj.core.api.Assertions.assertThat;

class InGatewayImplTest {

	@Test
	void shouldCheckShutdown() {
		TestBootstrapHolder holder = new TestBootstrapHolder(false, null);
		InGatewayImpl inGateway = new InGatewayImpl(
			holder,
			new TestFutureProcessor()
		);

		inGateway.shutdown();
		assertThat(holder.isShutdown()).isTrue();
	}

	@Test
	void shouldCheckShutdown_onStart() {
		TestBootstrapHolder holder = new TestBootstrapHolder(true, null);
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
		TestBootstrapHolder holder = new TestBootstrapHolder(false, expectedFuture);
		TestFutureProcessor futureProcessor = new TestFutureProcessor();
		InGatewayImpl inGateway = new InGatewayImpl(
			holder,
			futureProcessor
		);

		inGateway.start();
		assertThat(futureProcessor.getChannelFuture()).isEqualTo(expectedFuture);
	}

	@RequiredArgsConstructor
	private static class TestBootstrapHolder implements BootstrapHolder {
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

		@Override
		public ServerBootstrap getBootstrap() {
			return null;
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
