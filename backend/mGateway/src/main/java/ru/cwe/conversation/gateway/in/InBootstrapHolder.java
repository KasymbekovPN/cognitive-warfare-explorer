package ru.cwe.conversation.gateway.in;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.gateway.BootstrapHolder;

public final class InBootstrapHolder implements BootstrapHolder {
	private final ServerBootstrap serverBootstrap;
	private final EventLoopGroup boss;
	private final EventLoopGroup worker;
	private final int port;

	public static Builder builder(){
		return new Builder();
	}

	private InBootstrapHolder(ServerBootstrap serverBootstrap, EventLoopGroup boss, EventLoopGroup worker, int port) {
		this.serverBootstrap = serverBootstrap;
		this.boss = boss;
		this.worker = worker;
		this.port = port;

		this.serverBootstrap.group(this.boss, this.worker);
	}

	@Override
	public ServerBootstrap getServerBootstrap() {
		return serverBootstrap;
	}

	@Override
	public void shutdown() {
		boss.shutdownGracefully();
		worker.shutdownGracefully();
	}

	@Override
	public ChannelFuture getFuture() throws InterruptedException{
		return serverBootstrap.bind(port).sync();
	}

	@RequiredArgsConstructor
	public static class Builder {

		private EventLoopGroup boss;
		private EventLoopGroup worker;

		public Builder boss(EventLoopGroup boss){
			this.boss = boss;
			return this;
		}

		public Builder worker(EventLoopGroup worker){
			this.worker = worker;
			return this;
		}

		public InBootstrapHolder build(ServerBootstrap bootstrap, int port){
			return new InBootstrapHolder(
				bootstrap,
				boss != null ? boss : new NioEventLoopGroup(),
				worker != null ? worker : new NioEventLoopGroup(),
				port
			);
		}
	}
}
