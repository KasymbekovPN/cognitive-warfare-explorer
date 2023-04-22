package ru.cwe.common.test.address;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.cwe.common.address.Address;

@EqualsAndHashCode
@Getter
public class TestAddress implements Address {
	private final String host;
	private final int port;

	public TestAddress(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public TestAddress() {
		this.host = "";
		this.port = 0;
	}
}
