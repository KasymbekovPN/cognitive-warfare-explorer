package ru.cwe.conversation.gateway.in;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class InGatewayImpl implements InGateway{
	private final ServerHolder serverHolder;
	private final FutureProcessor futureProcessor;

	@Override
	public void start() {
		try{
			futureProcessor.process(serverHolder.getFuture());
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
