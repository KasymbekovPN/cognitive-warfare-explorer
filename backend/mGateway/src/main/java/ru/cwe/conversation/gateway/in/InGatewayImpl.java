package ru.cwe.conversation.gateway.in;

import io.netty.channel.ChannelFuture;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public final class InGatewayImpl implements InGateway{
	private final ServerHolder serverHolder;
	private final Consumer<ChannelFuture> futureProcessor;

	@Override
	public void start() {
		try{
			futureProcessor.accept(serverHolder.getFuture());
		} catch (Throwable ignored){}
		finally {
			serverHolder.shutdown();
		}
	}

	@Override
	public void shutdown() {
		serverHolder.shutdown();
	}
}
