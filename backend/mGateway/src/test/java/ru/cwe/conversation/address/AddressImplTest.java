package ru.cwe.conversation.address;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressImplTest {
	private static final String EXPECTED_HOST = "some.host";
	private static final int EXPECTED_PORT = 8080;

	@Test
	void shouldCheckHostGetting() {
		String host = new AddressImpl(EXPECTED_HOST, EXPECTED_PORT).getHost();

		assertThat(host).isEqualTo(EXPECTED_HOST);
	}

	@Test
	void shouldCheckPortGetting() {
		int port = new AddressImpl(EXPECTED_HOST, EXPECTED_PORT).getPort();

		assertThat(port).isEqualTo(EXPECTED_PORT);
	}
}
