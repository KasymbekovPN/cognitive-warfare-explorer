package ru.cwe.conversation.gateway.in;

import io.netty.channel.ChannelFuture;
import ru.cwe.common.shutdown.Shutdown;

public interface ServerBootstrapHolder extends Shutdown {
	ChannelFuture getFuture() throws InterruptedException;
}
