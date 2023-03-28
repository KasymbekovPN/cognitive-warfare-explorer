package ru.cwe.conversation.address;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.cwe.utils.reflection.Reflections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AddressBuilderTest {

	@ParameterizedTest
	@CsvFileSource(resources = "shouldCheckFailBuilding.csv")
	void shouldCheckFailBuilding(String host, Integer port, String expectedMessage) {
		Throwable throwable = catchThrowable(() -> {
			AddressBuilder builder = AddressBuilder.builder().host(host);
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
		Address address = AddressBuilder.builder()
			.host(expectedHost)
			.port(expectedPort)
			.build();

		assertThat(address.getPort()).isEqualTo(expectedPort);
		assertThat(address.getHost()).isEqualTo(expectedHost);
	}

	@SneakyThrows
	@Test
	void shouldCheckReset() {
		String expectedHost = "some.host";
		int expectedPort = 8080;
		AddressBuilder builder = AddressBuilder.builder()
			.host(expectedHost)
			.port(expectedPort)
			.reset();

		assertThat(Reflections.get(builder, "host", String.class)).isNull();
		assertThat(Reflections.get(builder, "port", Integer.class)).isNull();
	}
}
