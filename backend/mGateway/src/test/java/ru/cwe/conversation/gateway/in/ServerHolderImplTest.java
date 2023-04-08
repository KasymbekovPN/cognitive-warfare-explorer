package ru.cwe.conversation.gateway.in;

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

class ServerHolderImplTest {

	@Test
	void shouldCheckGroupsBinding() {
		TestServerBootstrap serverBootstrap = new TestServerBootstrap();
		NioEventLoopGroup boos = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();
		ServerHolderImpl holder = new ServerHolderImpl(
			serverBootstrap,
			boos,
			worker,
			Fakers.address().port()
		);

		assertThat(serverBootstrap.getBoos()).isEqualTo(boos);
		assertThat(serverBootstrap.getWorker()).isEqualTo(worker);
	}

	@Test
	void shouldCheckShutdown() {
		TestEventLoopGroup boos = new TestEventLoopGroup();
		TestEventLoopGroup worker = new TestEventLoopGroup();
		ServerHolderImpl holder = new ServerHolderImpl(
			new TestServerBootstrap(),
			boos,
			worker,
			Fakers.address().port()
		);

		holder.shutdown();
		assertThat(boos.isSd()).isTrue();
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

		int port = Fakers.address().port();
		ServerHolderImpl holder = new ServerHolderImpl(
			serverBootstrap,
			new NioEventLoopGroup(),
			new NioEventLoopGroup(),
			port
		);

		ChannelFuture future = holder.getFuture();
		assertThat(future).isInstanceOf(TestChannelFutureImpl.class);
		TestChannelFutureImpl castFuture = (TestChannelFutureImpl) future;
		assertThat(castFuture.getPort()).isEqualTo(port);
	}

	@Getter
	private static class TestServerBootstrap extends ServerBootstrap {
		private EventLoopGroup boos;
		private EventLoopGroup worker;

		@Override
		public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup) {
			this.boos = parentGroup;
			this.worker = childGroup;
			return this;
		}

		@Override
		public ChannelFuture bind(int inetPort) {
			return new TestChannelFutureImpl(inetPort);
		}
	}

	@RequiredArgsConstructor
	@Getter
	private static class TestChannelFutureImpl extends TestChannelFuture {
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
