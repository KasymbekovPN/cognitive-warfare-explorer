package ru.cwe.conversation.message.payload;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.MessageType;
import utils.TestAddress;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PayloadMessageImplTest {
	private static final UUID UUID = java.util.UUID.randomUUID();
	private static final MessageType TYPE = MessageType.REQUEST;
	private static final String CONTENT_TYPE = "some.content.type";
	private static final String CONTENT = "some.content";
	private static final Address FROM = new TestAddress("from.host", 8080);
	private static final Address TO = new TestAddress("to.host", 8081);

	private static PayloadMessageImpl message;

	@BeforeAll
	static void beforeAll() {
		message = new PayloadMessageImpl(
			-1, // TODO: 30.03.2023 temp
			TYPE,
			UUID,
			CONTENT_TYPE,
			CONTENT,
			FROM,
			TO
		);
	}

	@Test
	void shouldCheckUuidGetting() {
		assertThat(message.getUuid()).isEqualTo(UUID);
	}

	@Test
	void shouldCheckTypeGetting() {
		assertThat(message.getType()).isEqualTo(TYPE);
	}

	@Test
	void shouldCheckContentTypeGetting() {
		assertThat(message.getContentType()).isEqualTo(CONTENT_TYPE);
	}

	@Test
	void shouldCheckContentGetting() {
		assertThat(message.getContent()).isEqualTo(CONTENT);
	}

	@Test
	void shouldCheckFromGetting() {
		assertThat(message.getFrom()).isEqualTo(FROM);
	}

	@Test
	void shouldCheckToGetting() {
		assertThat(message.getTo()).isEqualTo(TO);
	}
}
