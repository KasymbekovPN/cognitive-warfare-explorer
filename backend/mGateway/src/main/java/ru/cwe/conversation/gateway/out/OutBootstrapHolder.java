package ru.cwe.conversation.gateway.out;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Getter;
import ru.cwe.conversation.gateway.BootstrapHolder;

public final class OutBootstrapHolder implements BootstrapHolder {
	@Getter
	private final ServerBootstrap bootstrap;
	private final EventLoopGroup worker;
	private final String host;
	private final int port;

	public static Builder builder(){
		return new Builder();
	}

	public static OutBootstrapHolder instance(ServerBootstrap bootstrap,
											  String host,
											  int port){
		return builder().build(bootstrap, host, port);
	}

	private OutBootstrapHolder(ServerBootstrap bootstrap,
							   EventLoopGroup worker,
							   String host,
							   int port) {
		this.bootstrap = bootstrap;
		this.worker = worker;
		this.host = host;
		this.port = port;

		this.bootstrap.group(this.worker);
	}

	@Override
	public void shutdown() {
		worker.shutdownGracefully();
	}

	@Override
	public ChannelFuture getFuture() throws InterruptedException {
		return bootstrap.bind(host, port).sync();
	}

	public static class Builder {
		private EventLoopGroup worker;

		public Builder worker(EventLoopGroup worker){
			this.worker = worker;
			return this;
		}

		public OutBootstrapHolder build(ServerBootstrap bootstrap,
										String host,
										int port){
			return new OutBootstrapHolder(
				bootstrap,
				worker != null ? worker : new NioEventLoopGroup(),
				host,
				port
			);
		}
	}
}
