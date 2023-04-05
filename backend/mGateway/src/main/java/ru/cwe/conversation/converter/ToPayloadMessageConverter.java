package ru.cwe.conversation.converter;

import ru.cwe.conversation.message.payload.PayloadMessage;

import java.util.Optional;
import java.util.function.Function;

public final class ToPayloadMessageConverter implements Function<Object, Optional<PayloadMessage>> {

	@Override
	public Optional<PayloadMessage> apply(final Object object) {
		return PayloadMessage.class.isAssignableFrom(object.getClass())
			? Optional.of((PayloadMessage) object)
			: Optional.empty();
	}
}
