// TODO: 01.04.2023 del
//package ru.cwe.conversation.reader.value;
//
//import io.netty.buffer.ByteBuf;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//import ru.cwe.conversation.message.MessageType;
//import utils.BufferUtil;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.catchThrowable;
//
//class MessageTypeByteBufferValueReaderTest {
//
//	@Test
//	void shouldCheckReading_ifFail() {
//		Throwable throwable = catchThrowable(() -> {
//			new MessageTypeByteBufferValueReader().read(
//				BufferUtil.create()
//			);
//		});
//
//		assertThat(throwable).isInstanceOf(IndexOutOfBoundsException.class);
//	}
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "shouldCheckReading.csv")
//	void shouldCheckReading(int intSource, String strSource) {
//		ByteBuf buffer = BufferUtil.create();
//		BufferUtil.writeInt(buffer, intSource);
//
//		MessageType type = new MessageTypeByteBufferValueReader().read(buffer);
//		assertThat(type).isEqualTo(MessageType.valueOf(strSource));
//	}
//}
