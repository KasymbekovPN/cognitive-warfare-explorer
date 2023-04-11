package ru.cwe.conversation.gateway.in;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.RequiredArgsConstructor;

public final class ServerBootstrapHolderImpl implements ServerBootstrapHolder {
	private final ServerBootstrap serverBootstrap;
	private final EventLoopGroup boss;
	private final EventLoopGroup worker;
	private final int port;

	public static Builder builder(){
		return new Builder();
	}

	public static ServerBootstrapHolderImpl instance(final ServerBootstrap serverBootstrap,
													 final int port){
		return builder().build(serverBootstrap, port);
	}

	private ServerBootstrapHolderImpl(final ServerBootstrap serverBootstrap,
									  final EventLoopGroup boss,
									  final EventLoopGroup worker,
									  final int port) {
		this.serverBootstrap = serverBootstrap;
		this.boss = boss;
		this.worker = worker;
		this.port = port;

		this.serverBootstrap.group(boss, worker);
	}

	@Override
	public void shutdown() {
		boss.shutdownGracefully();
		worker.shutdownGracefully();
	}

	@Override
	public ChannelFuture getFuture() throws InterruptedException {
		return serverBootstrap.bind(port).sync();
	}

	@RequiredArgsConstructor
	public static class Builder {

		private EventLoopGroup boss;
		private EventLoopGroup worker;

		public Builder boss(final EventLoopGroup boss){
			this.boss = boss;
			return this;
		}

		public Builder worker(final EventLoopGroup worker){
			this.worker = worker;
			return this;
		}

		public ServerBootstrapHolderImpl build(final ServerBootstrap bootstrap,
											   final int port){
			return new ServerBootstrapHolderImpl(
				bootstrap,
				boss != null ? boss : new NioEventLoopGroup(),
				worker != null ? worker : new NioEventLoopGroup(),
				port
			);
		}
	}
}
