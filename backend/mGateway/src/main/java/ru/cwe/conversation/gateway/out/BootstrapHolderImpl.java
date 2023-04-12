package ru.cwe.conversation.gateway.out;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Getter;

public final class BootstrapHolderImpl implements BootstrapHolder {
	@Getter private final Bootstrap bootstrap;
	private final EventLoopGroup worker;
	private final String host;
	private final int port;

	public static Builder builder(){
		return new Builder();
	}

	public static BootstrapHolderImpl instance(final Bootstrap bootstrap,
											   final String host,
											   final int port){
		return builder().build(bootstrap, host, port);
	}

	private BootstrapHolderImpl(final Bootstrap bootstrap,
								final EventLoopGroup worker,
								final String host,
								final int port) {
		this.bootstrap = bootstrap;
		this.worker = worker;
		this.host = host;
		this.port = port;

		this.bootstrap.group(worker);
	}

	@Override
	public void shutdown() {
		worker.shutdownGracefully();
	}

	@Override
	public ChannelFuture getFuture() throws InterruptedException {
		return getFuture(host, port);
	}

	@Override
	public ChannelFuture getFuture(final String host,
								   final int port) throws InterruptedException {
		return bootstrap.connect(host, port).sync();
	}

	public static class Builder {
		private EventLoopGroup worker;

		public Builder worker(final EventLoopGroup worker){
			this.worker = worker;
			return this;
		}

		public BootstrapHolderImpl build(final Bootstrap bootstrap,
										 final String host,
										 final int port){
			return new BootstrapHolderImpl(
				bootstrap,
				worker != null ? worker : new NioEventLoopGroup(),
				host,
				port
			);
		}
	}
}
