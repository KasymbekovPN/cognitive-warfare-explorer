package utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.cwe.conversation.address.Address;

@EqualsAndHashCode
@Getter
public final class TestAddress implements Address {
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
