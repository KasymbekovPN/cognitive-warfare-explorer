package ru.cwe.conversation.gateway;

import io.netty.channel.ChannelFuture;

public final class FutureProcessorImpl implements FutureProcessor {

	@Override
	public void process(ChannelFuture future) throws InterruptedException{
		future.channel().closeFuture().sync();
	}
}
