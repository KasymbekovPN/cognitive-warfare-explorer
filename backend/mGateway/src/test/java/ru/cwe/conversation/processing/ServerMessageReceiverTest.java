package ru.cwe.conversation.processing;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;
import ru.cwe.conversation.message.payload.PayloadMessage;
import utils.TestConfirmationMessage;
import utils.TestContainer;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import java.net.SocketAddress;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class ServerMessageReceiverTest {

	@SneakyThrows
	@Test
	void shouldCheckChannelReading_ifObjectIsNotPayloadMessage() {
		Object message = new Object();
		TestContainer requestContainer = new TestContainer();
		TestContainer responseContainer = new TestContainer();
		ServerMessageReceiver processing = new ServerMessageReceiver(
			requestContainer,
			responseContainer,
			createConverter(null, message),
			null
		);

		processing.channelRead(null, message);
		assertThat(requestContainer.getMessages()).isEmpty();
		assertThat(responseContainer.getMessages()).isEmpty();
	}

	@SneakyThrows
	@Test
	void shouldCheckChannelReading_ifRequest() {
		TestPayloadMessage payloadMessage = new TestPayloadMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			MessageType.REQUEST,
			Fakers.base().uuid().uuid(),
			Fakers.base().string().string(),
			Fakers.base().string().string(),
			Fakers.address().address(),
			Fakers.address().address()
		);

		TestConfirmationMessage confirmationMessage = new TestConfirmationMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			Fakers.base().uuid().uuid(),
			ConfirmationResult.REQUEST,
			Fakers.base().string().string()
		);

		TestContainer requestContainer = new TestContainer();
		TestContainer responseContainer = new TestContainer();
		ServerMessageReceiver processing = new ServerMessageReceiver(
			requestContainer,
			responseContainer,
			createConverter(payloadMessage, payloadMessage),
			createConfirmationConverter(confirmationMessage, payloadMessage)
		);

		TestChannelHandlerContext ctx = new TestChannelHandlerContext();
		processing.channelRead(ctx, payloadMessage);
		assertThat(requestContainer.getMessages().size()).isEqualTo(1);
		assertThat(responseContainer.getMessages()).isEmpty();
		assertThat(ctx.getMsg()).isEqualTo(confirmationMessage);
		assertThat(ctx.getFuture().getListener()).isEqualTo(ChannelFutureListener.CLOSE);
	}

	@SneakyThrows
	@Test
	void shouldCheckChannelReading_ifResponse() {
		TestPayloadMessage payloadMessage = new TestPayloadMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			MessageType.RESPONSE,
			Fakers.base().uuid().uuid(),
			Fakers.base().string().string(),
			Fakers.base().string().string(),
			Fakers.address().address(),
			Fakers.address().address()
		);

		TestConfirmationMessage confirmationMessage = new TestConfirmationMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			Fakers.base().uuid().uuid(),
			ConfirmationResult.RESPONSE,
			Fakers.base().string().string()
		);

		TestContainer requestContainer = new TestContainer();
		TestContainer responseContainer = new TestContainer();
		ServerMessageReceiver processing = new ServerMessageReceiver(
			requestContainer,
			responseContainer,
			createConverter(payloadMessage, payloadMessage),
			createConfirmationConverter(confirmationMessage, payloadMessage)
		);

		TestChannelHandlerContext ctx = new TestChannelHandlerContext();
		processing.channelRead(ctx, payloadMessage);
		assertThat(requestContainer.getMessages()).isEmpty();;
		assertThat(responseContainer.getMessages().size()).isEqualTo(1);
		assertThat(ctx.getMsg()).isEqualTo(confirmationMessage);
		assertThat(ctx.getFuture().getListener()).isEqualTo(ChannelFutureListener.CLOSE);
	}

	private TestToPayloadConverter createConverter(PayloadMessage message, Object object){
		Optional<PayloadMessage> result = message != null ? Optional.of(message) : Optional.empty();
		TestToPayloadConverter converter = Mockito.mock(TestToPayloadConverter.class);
		Mockito
			.when(converter.apply(object))
			.thenReturn(result);

		return converter;
	}

	private TestToConfirmationConverter createConfirmationConverter(ConfirmationMessage confirmationMessage, PayloadMessage message){
		TestToConfirmationConverter converter = Mockito.mock(TestToConfirmationConverter.class);
		Mockito
			.when(converter.apply(message))
			.thenReturn(confirmationMessage);

		return converter;
	}

	private static abstract class TestToPayloadConverter implements Function<Object, Optional<PayloadMessage>>{}
	private static abstract class TestToConfirmationConverter implements Function<PayloadMessage, ConfirmationMessage>{}

	@Getter
	private static class TestChannelHandlerContext implements ChannelHandlerContext{
		private Object msg;
		private TestChannelFuture future;

		@Override
		public ChannelFuture writeAndFlush(Object msg) {
			this.msg = msg;
			this.future = new TestChannelFuture();

			return this.future;
		}

		@Override
		public Channel channel() { return null; }
		@Override
		public EventExecutor executor() { return null; }
		@Override
		public String name() { return null; }
		@Override
		public ChannelHandler handler() { return null; }
		@Override
		public boolean isRemoved() { return false; }
		@Override
		public ChannelHandlerContext fireChannelRegistered() { return null; }
		@Override
		public ChannelHandlerContext fireChannelUnregistered() { return null; }
		@Override
		public ChannelHandlerContext fireChannelActive() { return null; }
		@Override
		public ChannelHandlerContext fireChannelInactive() { return null; }
		@Override
		public ChannelHandlerContext fireExceptionCaught(Throwable cause) { return null; }
		@Override
		public ChannelHandlerContext fireUserEventTriggered(Object evt) { return null; }
		@Override
		public ChannelHandlerContext fireChannelRead(Object msg) {
			return null; }
		@Override
		public ChannelHandlerContext fireChannelReadComplete() { return null; }
		@Override
		public ChannelHandlerContext fireChannelWritabilityChanged() { return null; }
		@Override
		public ChannelHandlerContext read() { return null; }
		@Override
		public ChannelHandlerContext flush() { return null; }
		@Override
		public ChannelPipeline pipeline() { return null; }
		@Override
		public ByteBufAllocator alloc() { return null; }
		@Override
		public <T> Attribute<T> attr(AttributeKey<T> key) { return null; }
		@Override
		public <T> boolean hasAttr(AttributeKey<T> key) { return false; }
		@Override
		public ChannelFuture bind(SocketAddress localAddress) { return null; }
		@Override
		public ChannelFuture connect(SocketAddress remoteAddress) { return null; }
		@Override
		public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) { return null; }
		@Override
		public ChannelFuture disconnect() { return null; }
		@Override
		public ChannelFuture close() { return null; }
		@Override
		public ChannelFuture deregister() { return null; }
		@Override
		public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) { return null; }
		@Override
		public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) { return null; }
		@Override
		public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) { return null; }
		@Override
		public ChannelFuture disconnect(ChannelPromise promise) { return null; }
		@Override
		public ChannelFuture close(ChannelPromise promise) { return null; }
		@Override
		public ChannelFuture deregister(ChannelPromise promise) { return null; }
		@Override
		public ChannelFuture write(Object msg) { return null; }
		@Override
		public ChannelFuture write(Object msg, ChannelPromise promise) { return null; }
		@Override
		public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) { return null; }
		@Override
		public ChannelPromise newPromise() { return null; }
		@Override
		public ChannelProgressivePromise newProgressivePromise() { return null; }
		@Override
		public ChannelFuture newSucceededFuture() { return null; }
		@Override
		public ChannelFuture newFailedFuture(Throwable cause) { return null; }
		@Override
		public ChannelPromise voidPromise() { return null; }
	}

	private static class TestChannelFuture implements ChannelFuture{
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
}
