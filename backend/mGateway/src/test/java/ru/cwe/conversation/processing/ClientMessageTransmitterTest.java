package ru.cwe.conversation.processing;

import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.cwe.conversation.holder.SentMessageHolder;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;
import ru.cwe.conversation.message.payload.PayloadMessage;
import utils.TestChannelHandlerContext;
import utils.TestConfirmationMessage;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import java.util.Optional;
import java.util.UUID;
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
		ClientMessageTransmitter transmitter = ClientMessageTransmitter.instance(payloadMessage, null);
		transmitter.channelActive(ctx);

		assertThat(ctx.getMsg()).isEqualTo(payloadMessage);
	}

	@SneakyThrows
	@Test
	void shouldCheckChannelReading_ifWrongMessageType() {
		Object object = new Object();
		TestChannelHandlerContext ctx = new TestChannelHandlerContext();
		TestSentMessageHolder holder = new TestSentMessageHolder();
		ClientMessageTransmitter.builder()
			.converter(createConverter(null, object))
			.build(null, holder)
			.channelRead(ctx, object);

		assertThat(ctx.isClosed()).isTrue();
		assertThat(holder.getUuid()).isNull();
	}

	@SneakyThrows
	@Test
	void shouldCheckChannelReading() {
		UUID expectedUuid = Fakers.base().uuid().uuid();
		TestConfirmationMessage message = new TestConfirmationMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			expectedUuid,
			ConfirmationResult.REQUEST,
			Fakers.base().string().string()
		);
		TestChannelHandlerContext ctx = new TestChannelHandlerContext();
		TestSentMessageHolder holder = new TestSentMessageHolder();
		ClientMessageTransmitter.builder()
			.converter(createConverter(message, message))
			.build(null, holder)
			.channelRead(ctx, message);

		assertThat(ctx.isClosed()).isTrue();
		assertThat(holder.getUuid()).isEqualTo(expectedUuid);
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

	private static abstract class TestConverter implements Function<Object, Optional<ConfirmationMessage>>{}

	private static class TestSentMessageHolder implements SentMessageHolder {
		@Getter
		private UUID uuid;

		@Override
		public boolean offer(PayloadMessage message) { return false; }

		@Override
		public boolean erase(UUID uuid) {
			this.uuid = uuid;
			return true;
		}

		@Override
		public Optional<PayloadMessage> poll() { return Optional.empty(); }
	}
}
