package ru.cwe.conversation.gateway.out;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.gateway.BootstrapHolder;
import ru.cwe.conversation.gateway.in.FutureProcessor;
import utils.TestChannelFuture;
import utils.TestMessageContainer;

import static org.assertj.core.api.Assertions.assertThat;

class OutGatewayImplTest {

	@Test
	void shouldCheckShutdown() {
		TestBootstrapHolder holder = new TestBootstrapHolder(false, null);
		new OutGatewayImpl(
			holder,
			new TestFutureProcessor(),
			new TestMessageContainer<>()
		).shutdown();

		assertThat(holder.isShutdown()).isTrue();
	}

	@Test
	void shouldCheckShutdown_onStart() {
		TestBootstrapHolder holder = new TestBootstrapHolder(true, null);
		new OutGatewayImpl(
			holder,
			new TestFutureProcessor(),
			new TestMessageContainer<>()
		).send(null);

		assertThat(holder.isShutdown()).isTrue();
	}

	@Test
	void shouldCheckStart() {
		TestChannelFuture expectedFuture = new TestChannelFuture();
		TestBootstrapHolder holder = new TestBootstrapHolder(false, expectedFuture);
		TestFutureProcessor futureProcessor = new TestFutureProcessor();
		new OutGatewayImpl(
			holder,
			futureProcessor,
			new TestMessageContainer<>()
		).send(null);

		assertThat(futureProcessor.getChannelFuture()).isEqualTo(expectedFuture);
	}

	@RequiredArgsConstructor
	private static class TestBootstrapHolder implements BootstrapHolder {
		private final boolean doThrow;
		private final ChannelFuture channelFuture;

		private final ServerBootstrap bootstrap = new ServerBootstrap();

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
			return bootstrap;
		}
	}

	private static class TestFutureProcessor implements FutureProcessor {
		@Getter
		private ChannelFuture channelFuture;

		@Override
		public void process(ChannelFuture future) throws InterruptedException {
			this.channelFuture = future;
		}
	}
}
