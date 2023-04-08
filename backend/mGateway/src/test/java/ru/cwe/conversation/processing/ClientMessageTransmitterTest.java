package ru.cwe.conversation.processing;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.messagesource.MessageSource;
import utils.TestChannelHandlerContext;
import utils.TestConfirmationMessage;
import utils.TestMessageContainer;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class ClientMessageTransmitterTest {

	@SneakyThrows
	@Test
	void shouldCheckChannelActivation() {
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

		TestChannelHandlerContext ctx = new TestChannelHandlerContext();
		ClientMessageTransmitter transmitter = ClientMessageTransmitter.instance(createMessageSource(payloadMessage), null);
		transmitter.channelActive(ctx);

		assertThat(ctx.getMsg()).isEqualTo(payloadMessage);
	}

	@SneakyThrows
	@Test
	void shouldCheckChannelReading_ifWrongMessageType() {
		Object object = new Object();
		TestChannelHandlerContext ctx = new TestChannelHandlerContext();
		TestMessageContainer<ConfirmationMessage> container = new TestMessageContainer<>();
		ClientMessageTransmitter.builder()
			.converter(createConverter(null, object))
			.build(null, container)
			.channelRead(ctx, object);

		assertThat(ctx.isClosed()).isTrue();
		assertThat(container.getMessages()).isEmpty();
	}

	@SneakyThrows
	@Test
	void shouldCheckChannelReading() {
		TestConfirmationMessage message = new TestConfirmationMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			Fakers.base().uuid().uuid(),
			ConfirmationResult.REQUEST,
			Fakers.base().string().string()
		);
		TestChannelHandlerContext ctx = new TestChannelHandlerContext();
		TestMessageContainer<ConfirmationMessage> container = new TestMessageContainer<>();
		ClientMessageTransmitter.builder()
			.converter(createConverter(message, message))
			.build(null, container)
			.channelRead(ctx, message);

		assertThat(ctx.isClosed()).isTrue();
		assertThat(container.getMessages()).isEqualTo(List.of(message));
	}

	private TestMessageSource createMessageSource(PayloadMessage message){
		TestMessageSource source = Mockito.mock(TestMessageSource.class);
		Mockito
			.when(source.next())
			.thenReturn(message);

		return source;
	}

	private TestConverter createConverter(ConfirmationMessage message, Object object){
		Optional<ConfirmationMessage> result = message != null
			? Optional.of(message)
			: Optional.empty();
		TestConverter converter = Mockito.mock(TestConverter.class);
		Mockito
			.when(converter.apply(object))
			.thenReturn(result);

		return converter;
	}

	private static abstract class TestMessageSource implements MessageSource<PayloadMessage>{}
	private static abstract class TestConverter implements Function<Object, Optional<ConfirmationMessage>>{}
}
