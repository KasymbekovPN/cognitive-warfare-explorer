package ru.cwe.conversation.filter;

import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.payload.PayloadMessage;

import java.util.Set;

@RequiredArgsConstructor
public final class ToAddressMessageFilter implements Filter<PayloadMessage> {
	private final Set<Address> addresses;

	@Override
	public boolean filter(final PayloadMessage message) {
		return addresses.contains(message.getTo());
	}
}
