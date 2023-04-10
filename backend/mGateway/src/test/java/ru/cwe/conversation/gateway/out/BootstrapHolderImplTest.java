package ru.cwe.conversation.gateway.out;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import utils.TestChannelFuture;
import utils.faker.Fakers;

import static org.assertj.core.api.Assertions.assertThat;

class BootstrapHolderImplTest {

	@Test
	void shouldCheckGroupsBinding() {
		TestBootstrap bootstrap = new TestBootstrap();
		NioEventLoopGroup worker = new NioEventLoopGroup();
		BootstrapHolderImpl.builder()
			.worker(worker)
			.build(bootstrap, Fakers.address().host(), Fakers.address().port());

		assertThat(bootstrap.getWorker()).isEqualTo(worker);
	}

	@Test
	void shouldCheckShutdown() {
		TestEventLoopGroup worker = new TestEventLoopGroup();
		BootstrapHolderImpl.builder()
			.worker(worker)
			.build(new TestBootstrap(), Fakers.address().host(), Fakers.address().port())
			.shutdown();

		assertThat(worker.isSd()).isTrue();
	}

	@SneakyThrows
	@Test
	void shouldCheckFutureGetting() {
		TestBootstrap bootstrap = new TestBootstrap();
		bootstrap
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {}
			})
			.option(ChannelOption.SO_KEEPALIVE, true);

		String expectedHost = Fakers.address().host();
		int expectedPort = Fakers.address().port();
		BootstrapHolderImpl holder = BootstrapHolderImpl.instance(bootstrap, expectedHost, expectedPort);

		ChannelFuture future = holder.getFuture();
		assertThat(future).isInstanceOf(TestChannelFutureImpl.class);
		TestChannelFutureImpl castFuture = (TestChannelFutureImpl) future;
		assertThat(castFuture.getHost()).isEqualTo(expectedHost);
		assertThat(castFuture.getPort()).isEqualTo(expectedPort);
	}

	@SneakyThrows
	@Test
	void shouldCheckFutureGetting_withHostAndPort() {
		TestBootstrap bootstrap = new TestBootstrap();
		bootstrap
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {}
			})
			.option(ChannelOption.SO_KEEPALIVE, true);

		BootstrapHolderImpl holder = BootstrapHolderImpl.instance(bootstrap, Fakers.address().host(), Fakers.address().port());

		String expectedHost = Fakers.address().host();
		int expectedPort = Fakers.address().port();
		ChannelFuture future = holder.getFuture(expectedHost, expectedPort);
		assertThat(future).isInstanceOf(TestChannelFutureImpl.class);
		TestChannelFutureImpl castFuture = (TestChannelFutureImpl) future;
		assertThat(castFuture.getHost()).isEqualTo(expectedHost);
		assertThat(castFuture.getPort()).isEqualTo(expectedPort);
	}

	@Getter
	private static class TestBootstrap extends Bootstrap {
		private EventLoopGroup worker;

		@Override
		public Bootstrap group(EventLoopGroup group) {
			this.worker = group;
			return this;
		}

		@Override
		public ChannelFuture bind(String inetHost, int inetPort) {
			return new TestChannelFutureImpl(inetHost, inetPort);
		}
	}

	@RequiredArgsConstructor
	@Getter
	private static class TestChannelFutureImpl extends TestChannelFuture {
		private final String host;
		private final int port;

		@Override
		public ChannelFuture sync() throws InterruptedException {
			return this;
		}
	}

	private static class TestEventLoopGroup extends NioEventLoopGroup {
		@Getter
		private boolean sd;

		@Override
		public Future<?> shutdownGracefully() {
			this.sd = true;
			return super.shutdownGracefully();
		}
	}
}
