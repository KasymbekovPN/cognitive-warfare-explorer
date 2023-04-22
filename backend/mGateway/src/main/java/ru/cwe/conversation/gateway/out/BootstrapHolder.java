package ru.cwe.conversation.gateway.out;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import ru.cwe.common.shutdown.Shutdown;

public interface BootstrapHolder extends Shutdown {
	Bootstrap getBootstrap();
	ChannelFuture getFuture() throws InterruptedException;
	ChannelFuture getFuture(String host, int port) throws InterruptedException;
}
