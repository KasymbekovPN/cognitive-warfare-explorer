package ru.cwe.common.test.faker;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.RepeatedTest;
import ru.cwe.common.address.Address;
import ru.cwe.common.port.Ports;
import ru.cwe.common.test.address.TestAddress;

import static org.assertj.core.api.Assertions.assertThat;

class AddressFakerTest {

	@RepeatedTest(1_000)
	void shouldCheckPortCreation() {
		AddressFaker faker = new AddressFaker(new BaseFaker(new Faker()));
		int port = faker.port();

		assertThat(Ports.checkInRange(port)).isZero();
	}

	@RepeatedTest(1_000)
	void shouldCheckHostCreation() {
		AddressFaker faker = new AddressFaker(new BaseFaker(new Faker()));
		String host = faker.host();

		String prefix = "host";
		assertThat(host).isNotNull();
		assertThat(host.substring(0, prefix.length())).isEqualTo(prefix);
	}

	@RepeatedTest(1_000)
	void shouldCheckAddressCreation() {
		AddressFaker faker = new AddressFaker(new BaseFaker(new Faker()));
		Address address = faker.address();

		String prefix = "host";
		assertThat(address).isInstanceOf(TestAddress.class);
		assertThat(address.getHost()).isNotNull();
		assertThat(address.getHost().substring(0, prefix.length())).isEqualTo(prefix);
		assertThat(Ports.checkInRange(address.getPort())).isZero();
	}
}
