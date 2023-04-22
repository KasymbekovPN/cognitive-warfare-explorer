package ru.cwe.common.test.address;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TestAddressTest {

	@Test
	void shouldCheckDefaultCreation() {
		TestAddress address = new TestAddress();
		assertThat(address.getHost()).isEmpty();
		assertThat(address.getPort()).isZero();
	}

	@Test
	void shouldCheckCreation() {
		String expectedHost = "some.host";
		int expectedPort = 8080;
		TestAddress address = new TestAddress(expectedHost, expectedPort);

		assertThat(address.getHost()).isEqualTo(expectedHost);
		assertThat(address.getPort()).isEqualTo(expectedPort);
	}
}
