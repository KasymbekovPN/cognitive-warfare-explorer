package ru.cwe.conversation.tube;

import org.junit.jupiter.api.Test;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.MessageType;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import static org.assertj.core.api.Assertions.assertThat;

class TubeOldDatumImplTest {

	@Test
	void shouldCheckCreation_byMessage() {
		Address toAddress = Fakers.address().address();
		TestPayloadMessage message = new TestPayloadMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			MessageType.REQUEST,
			Fakers.base().uuid().uuid(),
			Fakers.base().string().string(),
			Fakers.base().string().string(),
			Fakers.address().address(),
			toAddress
		);

		TubeDatumImpl datum = new TubeDatumImpl(message);
		assertThat(datum.getMessage()).isEqualTo(message);
		assertThat(datum.getHost()).isEqualTo(toAddress.getHost());
		assertThat(datum.getPort()).isEqualTo(toAddress.getPort());
	}

	@Test
	void shouldCheckCreation_completely() {
		TestPayloadMessage message = new TestPayloadMessage(
			Fakers.message().version(),
			Fakers.message().priority(),
			MessageType.REQUEST,
			Fakers.base().uuid().uuid(),
			Fakers.base().string().string(),
			Fakers.base().string().string(),
			Fakers.address().address(),
			Fakers.address().address()
		);

		String expectedHost = Fakers.address().host();
		int expectedPort = Fakers.address().port();
		TubeDatumImpl datum = new TubeDatumImpl(message, expectedHost, expectedPort);
		assertThat(datum.getMessage()).isEqualTo(message);
		assertThat(datum.getHost()).isEqualTo(expectedHost);
		assertThat(datum.getPort()).isEqualTo(expectedPort);
	}
}
