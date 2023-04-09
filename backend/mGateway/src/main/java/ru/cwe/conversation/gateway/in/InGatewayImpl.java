package ru.cwe.conversation.gateway.in;

import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.gateway.BootstrapHolder;

@RequiredArgsConstructor
public final class InGatewayImpl implements InGateway{
	private final BootstrapHolder bootstrapHolder;
	private final FutureProcessor futureProcessor;

	@Override
	public void start() {
		try{
			futureProcessor.process(bootstrapHolder.getFuture());
		} catch (Throwable ignored){}
		finally {
			bootstrapHolder.shutdown();
		}
	}

	@Override
	public void shutdown() {
		bootstrapHolder.shutdown();
	}
}
