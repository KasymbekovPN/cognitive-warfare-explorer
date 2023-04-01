package ru.cwe.conversation.address;

import org.junit.jupiter.api.Test;
import utils.faker.Fakers;

import static org.assertj.core.api.Assertions.assertThat;

class AddressImplTest {

	@Test
	void shouldCheckHostGetting() {
		String expectedHost = Fakers.host();
		String host = new AddressImpl(expectedHost, Fakers.port()).getHost();

		assertThat(host).isEqualTo(expectedHost);
	}

	@Test
	void shouldCheckPortGetting() {
		Integer expectedPort = Fakers.port();
		int port = new AddressImpl(Fakers.host(), expectedPort).getPort();

		assertThat(port).isEqualTo(expectedPort);
	}
}
