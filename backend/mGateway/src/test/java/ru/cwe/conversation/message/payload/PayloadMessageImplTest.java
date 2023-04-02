package ru.cwe.conversation.message.payload;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.MessageType;
import utils.faker.Fakers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PayloadMessageImplTest {
	private PayloadMessageImpl message;
	private int expectedVersion;
	private int expectedPriority;
	private MessageType expectedType;
	private UUID expectedUuid;
	private String expectedContentType;
	private String expectedContent;
	private Address expectedFrom;
	private Address expectedTo;

	@BeforeEach
	void setUp() {
		expectedVersion = Fakers.message().version();
		expectedPriority = Fakers.message().priority();
		expectedType = MessageType.REQUEST;
		expectedUuid = Fakers.base().uuid().uuid();
		expectedContentType = Fakers.base().string().string();
		expectedContent = Fakers.base().string().string();
		expectedFrom = Fakers.address().address();
		expectedTo = Fakers.address().address();
		message = new PayloadMessageImpl(
			expectedVersion,
			expectedPriority,
			expectedType,
			expectedUuid,
			expectedContentType,
			expectedContent,
			expectedFrom,
			expectedTo
		);
	}

	@Test
	void shouldCheckVersionGetting() {
		assertThat(message.getVersion()).isEqualTo(expectedVersion);
	}

	@Test
	void shouldCheckPriorityGetting() {
		assertThat(message.getPriority()).isEqualTo(expectedPriority);
	}

	@Test
	void shouldCheckUuidGetting() {
		assertThat(message.getUuid()).isEqualTo(expectedUuid);
	}

	@Test
	void shouldCheckTypeGetting() {
		assertThat(message.getType()).isEqualTo(expectedType);
	}

	@Test
	void shouldCheckContentTypeGetting() {
		assertThat(message.getContentType()).isEqualTo(expectedContentType);
	}

	@Test
	void shouldCheckContentGetting() {
		assertThat(message.getContent()).isEqualTo(expectedContent);
	}

	@Test
	void shouldCheckFromGetting() {
		assertThat(message.getFrom()).isEqualTo(expectedFrom);
	}

	@Test
	void shouldCheckToGetting() {
		assertThat(message.getTo()).isEqualTo(expectedTo);
	}
}
