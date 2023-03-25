package ru.cwe.conversation.address;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AddressBuilderTest {

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckFailBuilding.csv")
	void shouldCheckFailBuilding(String host, Integer port, String expectedMessage) {
		Throwable throwable = catchThrowable(() -> {
			AddressBuilder builder = new AddressBuilder().host(host);
			if (port != null){
				builder.port(port);
			}
			builder.build();
		});

		assertThat(throwable)
			.isInstanceOf(AddressBuildingRuntimeException.class)
			.hasMessage(expectedMessage);
	}

	@Test
	void shouldCheckBuilding() {
		String expectedHost = "some.host";
		int expectedPort = 8080;
		Address address = new AddressBuilder()
			.host(expectedHost)
			.port(expectedPort)
			.build();

		assertThat(address.getPort()).isEqualTo(expectedPort);
		assertThat(address.getHost()).isEqualTo(expectedHost);
	}
}
