package ru.cwe.conversation.gateway.in;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.cwe.utils.reflection.Reflections;
import utils.TestMessageContainer;
import utils.faker.Fakers;

import static org.assertj.core.api.Assertions.assertThat;

class InFactoryImplTest {

	@Test
	void shouldCheckCreation() {
		InGateway inGateway = new InFactoryImpl().create(
			new TestMessageContainer<>(),
			new TestMessageContainer<>(),
			Fakers.address().port()
		);

		assertThat(inGateway).isInstanceOf(InGatewayImpl.class);
	}

	@SneakyThrows
	@Test
	void shouldCheckCreation_holder() {
		int expectedPort = Fakers.address().port();
		InGateway inGateway = new InFactoryImpl().create(
			new TestMessageContainer<>(),
			new TestMessageContainer<>(),
			expectedPort
		);

		ServerHolderImpl holder = Reflections.get(inGateway, "serverHolder", ServerHolderImpl.class);
		assertThat(holder).isNotNull();

		ServerBootstrap bootstrap = Reflections.get(holder, "serverBootstrap", ServerBootstrap.class);
		NioEventLoopGroup boss = Reflections.get(holder, "boss", NioEventLoopGroup.class);
		NioEventLoopGroup worker = Reflections.get(holder, "worker", NioEventLoopGroup.class);
		Integer port = Reflections.get(holder, "port", Integer.class);

		assertThat(bootstrap).isNotNull();
		assertThat(boss).isNotNull();
		assertThat(worker).isNotNull();
		assertThat(port).isEqualTo(expectedPort);
	}

	@SneakyThrows
	@Test
	void shouldCheckCreation_FutureProcessor() {
		InGateway inGateway = new InFactoryImpl().create(
			new TestMessageContainer<>(),
			new TestMessageContainer<>(),
			Fakers.address().port()
		);

		FutureProcessorImpl processor = Reflections.get(inGateway, "futureProcessor", FutureProcessorImpl.class);
		assertThat(processor).isNotNull();
	}
}
