// TODO: 23.03.2023 ??
//package ru.cwe.conversation.message;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import utils.TestAddress;
//import utils.TestContentType;
//
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class MessageImplTest {
//	private static final boolean EXPECTED_IS_RESPONSE = true;
//	private static final String EXPECTED_CONTENT = "some.message";
//	private static final String EXPECTED_TYPE = "message.type";
//	private static final String EXPECTED_FROM_HOST = "from.host";
//	private static final int EXPECTED_FROM_PORT = 1;
//	private static final String EXPECTED_TO_HOST = "to.host";
//	private static final int EXPECTED_TO_PORT = 2;
//
//	private MessageImpl message;
//	private UUID expectedUuid;
//
//	@BeforeEach
//	void setUp() {
//		expectedUuid = UUID.randomUUID();
//		message = new MessageImpl(
//			EXPECTED_IS_RESPONSE,
//			expectedUuid,
//			new TestContentType(EXPECTED_TYPE),
//			EXPECTED_CONTENT,
//			new TestAddress(EXPECTED_FROM_HOST, EXPECTED_FROM_PORT),
//			new TestAddress(EXPECTED_TO_HOST, EXPECTED_TO_PORT)
//		);
//	}
//
//	@Test
//	void shouldCheckIsResponse() {
//		assertThat(message.isResponse()).isEqualTo(EXPECTED_IS_RESPONSE);
//	}
//
//	@Test
//	void shouldCheckUuidGetting() {
//		assertThat(message.getUuid()).isEqualTo(expectedUuid);
//	}
//
//	@Test
//	void shouldCheckTypeGetting() {
//		assertThat(message.getType()).isEqualTo(new TestContentType(EXPECTED_TYPE));
//	}
//
//	@Test
//	void shouldCheckContentGetting() {
//		assertThat(message.getContent()).isEqualTo(EXPECTED_CONTENT);
//	}
//
//	@Test
//	void shouldCheckFromGetting() {
//		assertThat(message.getFrom()).isEqualTo(new TestAddress(EXPECTED_FROM_HOST, EXPECTED_FROM_PORT));
//	}
//
//	@Test
//	void shouldCheckToGetting() {
//		assertThat(message.getTo()).isEqualTo(new TestAddress(EXPECTED_TO_HOST, EXPECTED_TO_PORT));
//	}
//}
