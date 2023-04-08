package ru.cwe.conversation.gateway.in;

import io.netty.channel.ChannelFuture;

public interface ServerHolder {
	void shutdown();
	ChannelFuture getFuture() throws InterruptedException;
}
