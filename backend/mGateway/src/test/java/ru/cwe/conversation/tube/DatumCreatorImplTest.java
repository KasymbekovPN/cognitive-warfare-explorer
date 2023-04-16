package ru.cwe.conversation.tube;

import lombok.Getter;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.utils.reflection.Reflections;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DatumCreatorImplTest {

	@SneakyThrows
	@Test
	void shouldCheckMessageMethod() {
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

		DatumCreatorImpl creator = new DatumCreatorImpl(new TestTube());
		DatumCreator result = creator.message(message);

		assertThat(result).isEqualTo(creator);
		assertThat(Reflections.get(creator, "message", PayloadMessage.class)).isEqualTo(message);
	}

	@SneakyThrows
	@Test
	void shouldCheckHostMethod() {
		DatumCreatorImpl creator = new DatumCreatorImpl(new TestTube());
		String expectedHost = Fakers.address().host();
		DatumCreator result = creator.host(expectedHost);

		assertThat(result).isEqualTo(creator);
		assertThat(Reflections.get(creator, "host", String.class)).isEqualTo(expectedHost);
	}

	@SneakyThrows
	@Test
	void shouldCheckPortMethod() {
		DatumCreatorImpl creator = new DatumCreatorImpl(new TestTube());
		int expectedPort = Fakers.address().port();
		DatumCreator result = creator.port(expectedPort);

		assertThat(result).isEqualTo(creator);
		assertThat(Reflections.get(creator, "port", Integer.class)).isEqualTo(expectedPort);
	}

	@Test
	void shouldCheckPutMethod() {
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

		Tube tube = new DatumCreatorImpl(new TestTube())
			.message(message)
			.host(expectedHost)
			.port(expectedPort)
			.put();
		assertThat(tube).isInstanceOf(TestTube.class);
		TestTube castTube = (TestTube) tube;
		TubeDatum datum = castTube.getDatum();
		assertThat(datum.getMessage()).isEqualTo(message);
		assertThat(datum.getHost()).isEqualTo(expectedHost);
		assertThat(datum.getPort()).isEqualTo(expectedPort);
	}

	private static class TestTube implements Tube {
		@Getter
		private TubeDatum datum;

		@Override
		public void send(TubeDatum datum) {
			this.datum = datum;
		}

		@Override
		public int size() { return 0; }
		@Override
		public DatumCreator creator() { return null; }
	}
}
