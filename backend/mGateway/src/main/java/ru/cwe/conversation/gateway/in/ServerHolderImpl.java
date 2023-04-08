package ru.cwe.conversation.gateway.in;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;

public final class ServerHolderImpl implements ServerHolder {
	private final ServerBootstrap serverBootstrap;
	private final NioEventLoopGroup boos;
	private final NioEventLoopGroup worker;
	private final int port;

	public ServerHolderImpl(ServerBootstrap serverBootstrap, NioEventLoopGroup boos, NioEventLoopGroup worker, int port) {
		this.serverBootstrap = serverBootstrap;
		this.boos = boos;
		this.worker = worker;
		this.port = port;

		this.serverBootstrap.group(this.boos, this.worker);
	}

	@Override
	public void shutdown() {
		boos.shutdownGracefully();
		worker.shutdownGracefully();
	}

	@Override
	public ChannelFuture getFuture() throws InterruptedException{
		return serverBootstrap.bind(port).sync();
	}
}
