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

	public static BootstrapHolderImpl instance(Bootstrap bootstrap,
											   String host,
											   int port){
		return builder().build(bootstrap, host, port);
	}

	private BootstrapHolderImpl(Bootstrap bootstrap,
								EventLoopGroup worker,
								String host,
								int port) {
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
	public ChannelFuture getFuture(final String host, final int port) throws InterruptedException {
		return bootstrap.bind(host, port).sync();
	}

	public static class Builder {
		private EventLoopGroup worker;

		public Builder worker(EventLoopGroup worker){
			this.worker = worker;
			return this;
		}

		public BootstrapHolderImpl build(Bootstrap bootstrap,
										   String host,
										   int port){
			return new BootstrapHolderImpl(
				bootstrap,
				worker != null ? worker : new NioEventLoopGroup(),
				host,
				port
			);
		}
	}
}
