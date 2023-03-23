// TODO: 23.03.2023 ???
//package ru.cwe.conversation.decoder;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.UnpooledByteBufAllocator;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.cwe.conversation.message.MessageOLd;
//
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class MessageDecoderTest {
//	private static final boolean EXPECTED_IS_RESPONSE = true;
//	private static final String EXPECTED_MESSAGE_TYPE = "some.message.type";
//	private static final String EXPECTED_CONTENT = "some.content";
//	private static final String EXPECTED_FROM_HOST = "from.host";
//	private static final int EXPECTED_FROM_PORT = 8080;
//	private static final String EXPECTED_TO_HOST = "to.host";
//	private static final int EXPECTED_TO_PORT = 8081;
//	private static final UUID EXPECTED_UUID = UUID.randomUUID();
//
//	private ByteBuf buffer;
//
//	@BeforeEach
//	void setUp() {
//		buffer = new UnpooledByteBufAllocator(true).buffer();
//
//		buffer.writeBoolean(EXPECTED_IS_RESPONSE)
//			.writeLong(EXPECTED_UUID.getLeastSignificantBits())
//			.writeLong(EXPECTED_UUID.getMostSignificantBits())
//			.writeInt(EXPECTED_MESSAGE_TYPE.length())
//			.writeCharSequence(EXPECTED_MESSAGE_TYPE, StandardCharsets.UTF_8);
//		buffer.writeInt(EXPECTED_CONTENT.length())
//			.writeCharSequence(EXPECTED_CONTENT, StandardCharsets.UTF_8);
//		buffer.writeInt(EXPECTED_FROM_HOST.length())
//			.writeCharSequence(EXPECTED_FROM_HOST, StandardCharsets.UTF_8);
//		buffer.writeInt(EXPECTED_FROM_PORT)
//			.writeInt(EXPECTED_TO_HOST.length())
//			.writeCharSequence(EXPECTED_TO_HOST, StandardCharsets.UTF_8);
//		buffer.writeInt(EXPECTED_TO_PORT);
//	}
//
//	@Test
//	void shouldCheckDecoding() throws Exception {
//		ArrayList<Object> out = new ArrayList<>();
//		new MessageDecoder().decode(null, buffer, out);
//
//		assertThat(out.size()).isEqualTo(1);
//
//		MessageOLd message = (MessageOLd) out.get(0);
//		assertThat(message.isResponse()).isEqualTo(EXPECTED_IS_RESPONSE);
//		assertThat(message.getUuid()).isEqualTo(EXPECTED_UUID);
//		assertThat(message.getType().getName()).isEqualTo(EXPECTED_MESSAGE_TYPE);
//		assertThat(message.getContent()).isEqualTo(EXPECTED_CONTENT);
//		assertThat(message.getFrom().getHost()).isEqualTo(EXPECTED_FROM_HOST);
//		assertThat(message.getFrom().getPort()).isEqualTo(EXPECTED_FROM_PORT);
//		assertThat(message.getTo().getHost()).isEqualTo(EXPECTED_TO_HOST);
//		assertThat(message.getTo().getPort()).isEqualTo(EXPECTED_TO_PORT);
//	}
//}
