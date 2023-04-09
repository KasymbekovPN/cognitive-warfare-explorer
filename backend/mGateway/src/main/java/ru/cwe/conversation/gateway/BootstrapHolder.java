package ru.cwe.conversation.gateway;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;

public interface BootstrapHolder {
	ServerBootstrap getBootstrap();
	void shutdown();
	ChannelFuture getFuture() throws InterruptedException;
}
