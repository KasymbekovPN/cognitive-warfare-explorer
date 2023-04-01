package utils.faker;

import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.address.Address;
import utils.TestAddress;

@RequiredArgsConstructor
public final class AddressFaker {
	private final BaseFaker base;

	public int port(){
		return base.numberBetween(0, 65535);
	}

	public String host(){
		return "host" + String.valueOf(base.digit());
	}

	public Address address(){
		return new TestAddress(host(), port());
	}
}
