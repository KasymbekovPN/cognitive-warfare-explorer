package ru.cwe.conversation.gateway.out;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import ru.cwe.utils.shutdown.Shutdown;

public interface BootstrapHolder extends Shutdown {
	Bootstrap getBootstrap();
	ChannelFuture getFuture() throws InterruptedException;
	ChannelFuture getFuture(String host, int port) throws InterruptedException;
}
