package ru.cwe.conversation.gateway.out;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.gateway.FutureProcessor;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.utils.reflection.Reflections;
import utils.TestMessageContainer;
import utils.faker.Fakers;

import static org.assertj.core.api.Assertions.assertThat;

class OutFactoryImplTest {
	@Test
	void shouldCheckCreation() {
		OutGateway outGateway = new OutFactoryImpl().create(
			new TestMessageContainer<>(),
			Fakers.address().host(),
			Fakers.address().port()
		);

		assertThat(outGateway).isInstanceOf(OutGatewayImpl.class);
	}

	@SneakyThrows
	@Test
	void shouldCheckCreation_holder() {
		String expectedHost = Fakers.address().host();
		int expectedPort = Fakers.address().port();
		OutGateway outGateway = new OutFactoryImpl().create(
			new TestMessageContainer<>(),
			expectedHost,
			expectedPort
		);

		OutBootstrapHolder holder = Reflections.get(outGateway, "holder", OutBootstrapHolder.class);
		assertThat(holder).isNotNull();

		Bootstrap bootstrap = Reflections.get(holder, "bootstrap", Bootstrap.class);
		EventLoopGroup worker = Reflections.get(holder, "worker", EventLoopGroup.class);
		String host = Reflections.get(holder, "host", String.class);
		Integer port = Reflections.get(holder, "port", Integer.class);

		assertThat(bootstrap).isNotNull();
		assertThat(worker).isNotNull();
		assertThat(host).isEqualTo(expectedHost);
		assertThat(port).isEqualTo(expectedPort);
	}

	@SneakyThrows
	@Test
	void shouldCheckCreation_futureProcessor() {
		OutGateway outGateway = new OutFactoryImpl().create(
			new TestMessageContainer<>(),
			Fakers.address().host(),
			Fakers.address().port()
		);

		FutureProcessor futureProcessor = Reflections.get(outGateway, "futureProcessor", FutureProcessor.class);
		assertThat(futureProcessor).isNotNull();
	}

	@SneakyThrows
	@Test
	void shouldCheckCreation_container() {
		TestMessageContainer<ConfirmationMessage> container = new TestMessageContainer<>();
		OutGateway outGateway = new OutFactoryImpl().create(
			container,
			Fakers.address().host(),
			Fakers.address().port()
		);

		Object confirmationContainer = Reflections.get(outGateway, "confirmationContainer");
		assertThat(confirmationContainer).isEqualTo(container);
	}
}
