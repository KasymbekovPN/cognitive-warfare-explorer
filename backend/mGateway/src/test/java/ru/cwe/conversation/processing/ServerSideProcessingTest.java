package ru.cwe.conversation.processing;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.Message;
import ru.cwe.conversation.type.MessageType;
import utils.TestMessage;
import utils.TestTube;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ServerSideProcessingTest {
	private static final boolean EXPECTED_RESULT_IS_SUCCESS = true;
	private static final String EXPECTED_RESULT_NAME = "some.result.name";

	@Test
	void shouldCheckCheckingResultIsSuccessGetting() {
		ServerSideProcessing.CheckingResult result
			= new ServerSideProcessing.CheckingResult(EXPECTED_RESULT_IS_SUCCESS, EXPECTED_RESULT_NAME);

		assertThat(result.isSuccess()).isEqualTo(EXPECTED_RESULT_IS_SUCCESS);
	}

	@Test
	void shouldCheckCheckingResult_getNameGetting() {
		ServerSideProcessing.CheckingResult result
			= new ServerSideProcessing.CheckingResult(EXPECTED_RESULT_IS_SUCCESS, EXPECTED_RESULT_NAME);

		assertThat(result.getName()).isEqualTo(EXPECTED_RESULT_NAME);
	}

	@Test
	void shouldCheckTypeChecking_ifItHasWrongType() {
		String object = "";
		ServerSideProcessing.TypeChecker checker = new ServerSideProcessing.TypeChecker();
		ServerSideProcessing.CheckingResult result = checker.apply(object);

		assertThat(result.isSuccess()).isFalse();
		assertThat(result.getName()).isEqualTo(object.getClass().getSimpleName());
	}

	@Test
	void shouldCheckTypeChecking() {
		SomeMessage message = new SomeMessage();
		ServerSideProcessing.TypeChecker checker = new ServerSideProcessing.TypeChecker();
		ServerSideProcessing.CheckingResult result = checker.apply(message);

		assertThat(result.isSuccess()).isTrue();
		assertThat(result.getName()).isEqualTo(message.getClass().getSimpleName());
	}

	@SneakyThrows
	@Test
	void shouldCheckChannelReading_ifMsgHasWrongType() {
		TestTube requestTube = new TestTube();
		TestTube responseTube = new TestTube();
		new ServerSideProcessing(requestTube, responseTube).channelRead(null, new Object());

		assertThat(requestTube.getMessages()).isEmpty();
		assertThat(responseTube.getMessages()).isEmpty();
	}

	@SneakyThrows
	@Test
	void shouldCheckChannelReading_ifMsgIsRequest() {
		TestMessage message = new TestMessage();
		message.setResponse(true);

		TestTube requestTube = new TestTube();
		TestTube responseTube = new TestTube();
		new ServerSideProcessing(requestTube, responseTube).channelRead(null, message);

		assertThat(requestTube.getMessages().size()).isEqualTo(1);
		assertThat(responseTube.getMessages()).isEmpty();
	}

	@SneakyThrows
	@Test
	void shouldCheckChannelReading_ifMsgIsResponse() {
		TestMessage message = new TestMessage();
		message.setResponse(false);

		TestTube requestTube = new TestTube();
		TestTube responseTube = new TestTube();
		new ServerSideProcessing(requestTube, responseTube).channelRead(null, message);

		assertThat(requestTube.getMessages()).isEmpty();
		assertThat(responseTube.getMessages().size()).isEqualTo(1);
	}

	private interface MidMessage extends Message{}

	private static class BaseMessage implements MidMessage {
		@Override
		public boolean isResponse() { return false; }
		@Override
		public UUID getUuid() { return null; }
		@Override
		public MessageType getType() { return null; }
		@Override
		public String getContent() { return null; }
		@Override
		public Address getFrom() { return null; }
		@Override
		public Address getTo() { return null; }
	}

	private static class SomeMessage extends BaseMessage {}
}
