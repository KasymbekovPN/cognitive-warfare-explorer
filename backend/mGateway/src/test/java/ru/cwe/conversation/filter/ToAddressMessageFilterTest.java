package ru.cwe.conversation.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.MessageType;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ToAddressMessageFilterTest {

	@Test
	void shouldCheckFilter_ifInvalidToAddress() {
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
		boolean result = new ToAddressMessageFilter(Set.of()).filter(message);

		assertThat(result).isFalse();
	}

	@Test
	void shouldCheckFilter() {
		Address toAddress = Fakers.address().address();
		Set<Address> addresses = Set.of(
			toAddress,
			Fakers.address().address(),
			Fakers.address().address()
		);

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
		boolean result = new ToAddressMessageFilter(addresses).filter(message);

		assertThat(result).isTrue();
	}
}
