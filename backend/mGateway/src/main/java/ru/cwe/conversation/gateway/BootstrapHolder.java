package ru.cwe.conversation.gateway;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;

public interface BootstrapHolder {
	default ServerBootstrap getServerBootstrap() {throw new UnsupportedOperationException("getServerBootstrap");};
	default Bootstrap getBootstrap() {throw new UnsupportedOperationException("getBootstrap");};
	void shutdown();
	ChannelFuture getFuture() throws InterruptedException;
	default ChannelFuture getFuture(String host, int port) throws InterruptedException {throw new UnsupportedOperationException("getFuture");}
}
