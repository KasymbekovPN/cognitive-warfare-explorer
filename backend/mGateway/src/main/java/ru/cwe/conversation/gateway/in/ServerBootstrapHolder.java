package ru.cwe.conversation.gateway.in;

import io.netty.channel.ChannelFuture;
import ru.cwe.utils.shutdown.Shutdown;

public interface ServerBootstrapHolder extends Shutdown {
	ChannelFuture getFuture() throws InterruptedException;
}
