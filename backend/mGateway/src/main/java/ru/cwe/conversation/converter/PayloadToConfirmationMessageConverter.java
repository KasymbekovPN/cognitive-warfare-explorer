package ru.cwe.conversation.converter;

import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.confirmation.ConfirmationMessageBuilder;
import ru.cwe.conversation.message.payload.PayloadMessage;

import java.util.function.Function;

public final class PayloadToConfirmationMessageConverter implements Function<PayloadMessage, ConfirmationMessage> {

	@Override
	public ConfirmationMessage apply(final PayloadMessage payloadMessage) {
		return ConfirmationMessageBuilder.builder()
				.fromPayloadMessage(payloadMessage)
				.build();
	}
}
