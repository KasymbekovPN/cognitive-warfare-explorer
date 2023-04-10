package ru.cwe.conversation.gateway.in;

import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.gateway.FutureProcessor;

@RequiredArgsConstructor
public final class InGatewayImpl implements InGateway{
	private final ServerBootstrapHolder serverBootstrapHolder;
	private final FutureProcessor futureProcessor;

	@Override
	public void start() {
		try{
			futureProcessor.process(serverBootstrapHolder.getFuture());
		} catch (Throwable ignored){}
		finally {
			serverBootstrapHolder.shutdown();
		}
	}

	@Override
	public void shutdown() {
		serverBootstrapHolder.shutdown();
	}
}
