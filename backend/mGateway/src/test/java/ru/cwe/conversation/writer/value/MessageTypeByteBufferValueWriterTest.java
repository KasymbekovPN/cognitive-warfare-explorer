// TODO: 01.04.2023 del
//package ru.cwe.conversation.writer.value;
//
//import io.netty.buffer.ByteBuf;
//import org.junit.jupiter.api.Test;
//import ru.cwe.conversation.message.MessageType;
//import utils.BufferUtil;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class MessageTypeByteBufferValueWriterTest {
//
//	@Test
//	void shouldCheckReading() {
//		MessageType expectedType = MessageType.REQUEST;
//		ByteBuf buffer = BufferUtil.create();
//		new MessageTypeByteBufferValueWriter().write(buffer, expectedType);
//
//		assertThat(BufferUtil.readMessageType(buffer)).isEqualTo(expectedType);
//	}
//}
