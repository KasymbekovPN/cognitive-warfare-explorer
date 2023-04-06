package utils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.Getter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestChannelFuture implements ChannelFuture {
	@Getter
	private GenericFutureListener<? extends Future<? super Void>> listener;

	@Override
	public ChannelFuture addListener(GenericFutureListener<? extends Future<? super Void>> listener) {
		this.listener = listener;
		return null;
	}

	@Override
	public Channel channel() { return null; }
	@Override
	public ChannelFuture addListeners(GenericFutureListener<? extends Future<? super Void>>... listeners) { return null; }
	@Override
	public ChannelFuture removeListener(GenericFutureListener<? extends Future<? super Void>> listener) { return null; }
	@Override
	public ChannelFuture removeListeners(GenericFutureListener<? extends Future<? super Void>>... listeners) { return null; }
	@Override
	public ChannelFuture sync() throws InterruptedException { return null; }
	@Override
	public ChannelFuture syncUninterruptibly() { return null; }
	@Override
	public ChannelFuture await() throws InterruptedException { return null; }
	@Override
	public ChannelFuture awaitUninterruptibly() { return null; }
	@Override
	public boolean isVoid() { return false; }
	@Override
	public boolean isSuccess() { return false; }
	@Override
	public boolean isCancellable() { return false; }
	@Override
	public Throwable cause() { return null; }
	@Override
	public boolean await(long timeout, TimeUnit unit) throws InterruptedException { return false; }
	@Override
	public boolean await(long timeoutMillis) throws InterruptedException { return false; }
	@Override
	public boolean awaitUninterruptibly(long timeout, TimeUnit unit) { return false; }
	@Override
	public boolean awaitUninterruptibly(long timeoutMillis) { return false; }
	@Override
	public Void getNow() { return null; }
	@Override
	public boolean cancel(boolean mayInterruptIfRunning) { return false; }
	@Override
	public boolean isCancelled() { return false; }
	@Override
	public boolean isDone() { return false; }
	@Override
	public Void get() throws InterruptedException, ExecutionException { return null; }
	@Override
	public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException { return null; }
}
