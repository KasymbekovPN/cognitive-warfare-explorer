package ru.cwe.conversation.gateway.out;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import utils.TestChannelFuture;
import utils.faker.Fakers;

import static org.assertj.core.api.Assertions.assertThat;

class OutBootstrapHolderTest {

	@Test
	void shouldCheckGroupsBinding() {
		TestServerBootstrap serverBootstrap = new TestServerBootstrap();
		NioEventLoopGroup worker = new NioEventLoopGroup();
		OutBootstrapHolder.builder()
			.worker(worker)
			.build(serverBootstrap, Fakers.address().host(), Fakers.address().port());

		assertThat(serverBootstrap.getWorker()).isEqualTo(worker);
	}

	@Test
	void shouldCheckShutdown() {
		TestEventLoopGroup worker = new TestEventLoopGroup();
		OutBootstrapHolder.builder()
			.worker(worker)
			.build(new TestServerBootstrap(), Fakers.address().host(), Fakers.address().port())
			.shutdown();

		assertThat(worker.isSd()).isTrue();
	}

	@SneakyThrows
	@Test
	void shouldCheckFutureGetting() {
		TestServerBootstrap serverBootstrap = new TestServerBootstrap();
		serverBootstrap
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {}
			})
			.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true);

		String host = Fakers.address().host();
		int port = Fakers.address().port();
		OutBootstrapHolder holder = OutBootstrapHolder.instance(
			serverBootstrap,
			host,
			port
		);

		ChannelFuture future = holder.getFuture();
		assertThat(future).isInstanceOf(TestChannelFutureImpl.class);
		TestChannelFutureImpl castFuture = (TestChannelFutureImpl) future;
		assertThat(castFuture.getHost()).isEqualTo(host);
		assertThat(castFuture.getPort()).isEqualTo(port);
	}

	@Getter
	private static class TestServerBootstrap extends ServerBootstrap {
		private EventLoopGroup worker;

		@Override
		public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup) {
			this.worker = childGroup;
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
