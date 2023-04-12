package ru.cwe.conversation.gateway.in;

import io.netty.channel.ChannelFuture;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.gateway.FutureProcessor;

@RequiredArgsConstructor
public final class InGatewayImpl implements InGateway{
	private final ServerBootstrapHolder serverBootstrapHolder;
	private final FutureProcessor futureProcessor;

	@Override
	public void start() {
		try{
			ChannelFuture future = serverBootstrapHolder.getFuture();
			futureProcessor.process(future);
		} catch (Throwable ex){
			ex.printStackTrace();
		}
		finally {
			serverBootstrapHolder.shutdown();
		}
	}

	@Override
	public void shutdown() {
		serverBootstrapHolder.shutdown();
	}
}
