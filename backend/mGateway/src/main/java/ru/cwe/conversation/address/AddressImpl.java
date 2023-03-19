package ru.cwe.conversation.address;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
final class AddressImpl implements Address {
	private final String host;
	private final int port;
}
