package ru.cwe.conversation.address;

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

	// TODO: 23.03.2023 add normal building test
}
