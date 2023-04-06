package ru.cwe.conversation.gateway.in;

import io.netty.channel.ChannelFuture;

public interface FutureProcessor {
	void process(ChannelFuture future) throws InterruptedException;
}
