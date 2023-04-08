package ru.cwe.conversation.processing;

import io.netty.channel.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;
import ru.cwe.conversation.message.payload.PayloadMessage;
import utils.TestChannelHandlerContext;
import utils.TestConfirmationMessage;
import utils.TestMessageContainer;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class ServerMessageReceiverTest {

	@SneakyThrows
	@Test
	void shouldCheckChannelReading_ifObjectIsNotPayloadMessage() {
		Object message = new Object();
		TestMessageContainer<PayloadMessage> requestContainer = new TestMessageContainer<>();
		TestMessageContainer<PayloadMessage> responseContainer = new TestMessageContainer<>();
		ServerMessageReceiver processing = ServerMessageReceiver.builder()
			.toPayloadConverter(createConverter(null, message))
			.build(requestContainer, responseContainer);

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

		TestMessageContainer<PayloadMessage> requestContainer = new TestMessageContainer<>();
		TestMessageContainer<PayloadMessage> responseContainer = new TestMessageContainer<>();
		ServerMessageReceiver processing = ServerMessageReceiver.builder()
			.toPayloadConverter(createConverter(payloadMessage, payloadMessage))
			.toConfirmationConverter(createConfirmationConverter(confirmationMessage, payloadMessage))
			.build(requestContainer, responseContainer);

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

		TestMessageContainer<PayloadMessage> requestContainer = new TestMessageContainer<>();
		TestMessageContainer<PayloadMessage> responseContainer = new TestMessageContainer<>();
		ServerMessageReceiver processing = ServerMessageReceiver.builder()
			.toPayloadConverter(createConverter(payloadMessage, payloadMessage))
			.toConfirmationConverter(createConfirmationConverter(confirmationMessage, payloadMessage))
			.build(requestContainer, responseContainer);

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
}
