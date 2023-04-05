package ru.cwe.conversation.converter;

import ru.cwe.conversation.message.confirmation.ConfirmationMessage;

import java.util.Optional;
import java.util.function.Function;

public final class ToConfirmationMessageConverter implements Function<Object, Optional<ConfirmationMessage>> {

	@Override
	public Optional<ConfirmationMessage> apply(final Object object) {
		return ConfirmationMessage.class.isAssignableFrom(object.getClass())
			? Optional.of((ConfirmationMessage) object)
			: Optional.empty();
	}
}
