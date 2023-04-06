package ru.cwe.conversation.gateway.in;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import utils.TestChannel;
import utils.TestChannelFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class FutureProcessorImplTest {

	@Test
	void shouldCheckProcessing_ifException() {
		Throwable throwable = catchThrowable(() -> {
			new FutureProcessorImpl().process(
				new TestChannelFutureImpl(true)
			);
		});

		assertThat(throwable).isInstanceOf(InterruptedException.class);
	}

	@SneakyThrows
	@Test
	void shouldCheckProcessing() {
		TestChannelFutureImpl future = new TestChannelFutureImpl(false);
		new FutureProcessorImpl().process(
			future
		);

		assertThat(future.isSyncCalled()).isTrue();
	}

	@RequiredArgsConstructor
	private static class TestChannelFutureImpl extends TestChannelFuture {
		private final boolean doThrow;

		@Getter
		private boolean syncCalled;

		@Override
		public Channel channel() {
			return new TestChannelImpl(this);
		}

		@Override
		public ChannelFuture sync() throws InterruptedException {
			if (doThrow){
				throw  new InterruptedException("");
			}
			this.syncCalled = true;
			return this;
		}
	}

	@RequiredArgsConstructor
	private static class TestChannelImpl extends TestChannel {
		private final ChannelFuture future;

		@Override
		public ChannelFuture closeFuture() {
			return future;
		}
	}
}
