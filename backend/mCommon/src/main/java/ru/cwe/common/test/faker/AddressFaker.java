package ru.cwe.common.test.faker;

import lombok.RequiredArgsConstructor;
import ru.cwe.common.address.Address;
import ru.cwe.common.port.Ports;
import ru.cwe.common.test.address.TestAddress;

@RequiredArgsConstructor
public final class AddressFaker {
	private final BaseFaker base;

	public int port(){
		return base.integer().between(Ports.MIN, Ports.MAX + 1);
	}

	public String host(){
		return "host" + String.valueOf(base.integer().value());
	}

	public Address address(){
		return new TestAddress(host(), port());
	}
}
