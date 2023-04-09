package ru.cwe.conversation.gateway;

import io.netty.channel.ChannelFuture;

public interface FutureProcessor {
	void process(ChannelFuture future) throws InterruptedException;
}
