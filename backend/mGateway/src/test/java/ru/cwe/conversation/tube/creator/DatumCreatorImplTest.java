package ru.cwe.conversation.tube.creator;

import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.tube.datum.TubeDatum;
import ru.cwe.conversation.tube.TubeOld;
import ru.cwe.common.reflection.Reflections;
import utils.TestPayloadMessage;
import utils.faker.Fakers;

import static org.assertj.core.api.Assertions.assertThat;

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

		DatumCreatorImpl creator = new DatumCreatorImpl(new TestTubeOld());
		DatumCreator result = creator.message(message);

		assertThat(result).isEqualTo(creator);
		assertThat(Reflections.get(creator, "message", PayloadMessage.class)).isEqualTo(message);
	}

	@SneakyThrows
	@Test
	void shouldCheckHostMethod() {
		DatumCreatorImpl creator = new DatumCreatorImpl(new TestTubeOld());
		String expectedHost = Fakers.address().host();
		DatumCreator result = creator.host(expectedHost);

		assertThat(result).isEqualTo(creator);
		assertThat(Reflections.get(creator, "host", String.class)).isEqualTo(expectedHost);
	}

	@SneakyThrows
	@Test
	void shouldCheckPortMethod() {
		DatumCreatorImpl creator = new DatumCreatorImpl(new TestTubeOld());
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

		TubeOld tubeOld = new DatumCreatorImpl(new TestTubeOld())
			.message(message)
			.host(expectedHost)
			.port(expectedPort)
			.put();
		assertThat(tubeOld).isInstanceOf(TestTubeOld.class);
		TestTubeOld castTube = (TestTubeOld) tubeOld;
		TubeDatum datum = castTube.getDatum();
		assertThat(datum.getMessage()).isEqualTo(message);
		assertThat(datum.getHost()).isEqualTo(expectedHost);
		assertThat(datum.getPort()).isEqualTo(expectedPort);
	}

	private static class TestTubeOld implements TubeOld {
		@Getter
		private TubeDatum datum;

		@Override
		public boolean put(TubeDatum datum) {
			this.datum = datum;
			return true;
		}

		@Override
		public void dispose() throws InterruptedException {}

		@Override
		public int size() { return 0; }
		@Override
		public DatumCreator creator() { return null; }
	}
}
