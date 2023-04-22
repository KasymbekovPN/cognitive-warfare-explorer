package utils.faker;

import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.address.Address;
import utils.TestAddress;

// TODO: 22.04.2023 del
@RequiredArgsConstructor
public final class AddressFaker {
	private final BaseFaker base;

	public int port(){
		return base.number().between(0, 65535);
	}

	public String host(){
		return "host" + String.valueOf(base.number().integer());
	}

	public Address address(){
		return new TestAddress(host(), port());
	}
}
