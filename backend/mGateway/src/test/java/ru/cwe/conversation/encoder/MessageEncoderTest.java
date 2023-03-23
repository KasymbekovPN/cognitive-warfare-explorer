// TODO: 23.03.2023 ???
//package ru.cwe.conversation.encoder;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.UnpooledByteBufAllocator;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.cwe.conversation.message.MessageOLd;
//import ru.cwe.conversation.message.MessageBuilder;
//import utils.TestAddress;
//import utils.TestContentType;
//
//import java.nio.charset.StandardCharsets;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class MessageEncoderTest {
//	private static final boolean EXPECTED_IS_RESPONSE = false;
//	private static final String EXPECTED_MESSAGE_TYPE = "some.message.type";
//	private static final String EXPECTED_CONTENT = "some.content";
//	private static final String EXPECTED_FROM_HOST = "from.host";
//	private static final int EXPECTED_FROM_PORT = 8080;
//	private static final String EXPECTED_TO_HOST = "to.host";
//	private static final int EXPECTED_TO_PORT = 8081;
//
//	private static MessageOLd message;
//	private static UUID expectedUuid;
//
//	private ByteBuf buffer;
//
//	@BeforeAll
//	static void beforeAll() {
//		message = new MessageBuilder()
//			.type(new TestContentType(EXPECTED_MESSAGE_TYPE))
//			.content(EXPECTED_CONTENT)
//			.from(new TestAddress(EXPECTED_FROM_HOST, EXPECTED_FROM_PORT))
//			.to(new TestAddress(EXPECTED_TO_HOST, EXPECTED_TO_PORT))
//			.build();
//		expectedUuid = message.getUuid();
//	}
//
//	@BeforeEach
//	void setUp() {
//		buffer = new UnpooledByteBufAllocator(true).buffer();
//	}
//
//	@Test
//	void shouldCheckEncoding() throws Exception {
//		MessageEncoder encoder = new MessageEncoder();
//		encoder.encode(null, message, buffer);
//
//		boolean isResponse = buffer.readBoolean();
//
//		long low = buffer.readLong();
//		long high = buffer.readLong();
//		UUID uuid = new UUID(high, low);
//
//		int messageTypeLen = buffer.readInt();
//		CharSequence messageType = buffer.readCharSequence(messageTypeLen, StandardCharsets.UTF_8);
//
//		int contentLen = buffer.readInt();
//		CharSequence content = buffer.readCharSequence(contentLen, StandardCharsets.UTF_8);
//
//		int fromHostLen = buffer.readInt();
//		CharSequence fromHost = buffer.readCharSequence(fromHostLen, StandardCharsets.UTF_8);
//
//		int fromPort = buffer.readInt();
//
//		int toHostLen = buffer.readInt();
//		CharSequence toHost = buffer.readCharSequence(toHostLen, StandardCharsets.UTF_8);
//
//		int toPort = buffer.readInt();
//
//		assertThat(isResponse).isEqualTo(EXPECTED_IS_RESPONSE);
//		assertThat(uuid).isEqualTo(expectedUuid);
//		assertThat(messageType).isEqualTo(EXPECTED_MESSAGE_TYPE);
//		assertThat(content).isEqualTo(EXPECTED_CONTENT);
//		assertThat(fromHost).isEqualTo(EXPECTED_FROM_HOST);
//		assertThat(fromPort).isEqualTo(EXPECTED_FROM_PORT);
//		assertThat(toHost).isEqualTo(EXPECTED_TO_HOST);
//		assertThat(toPort).isEqualTo(EXPECTED_TO_PORT);
//	}
//}
