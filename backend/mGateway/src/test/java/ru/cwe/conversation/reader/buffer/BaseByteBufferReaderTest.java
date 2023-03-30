// TODO: 30.03.2023 restore
//package ru.cwe.conversation.reader.buffer;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.UnpooledByteBufAllocator;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import ru.cwe.conversation.message.Message;
//import utils.TestConfirmationMessage;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class BaseByteBufferReaderTest {
//
//	@Test
//	void shouldCheckFailReading() {
//		Optional<Message> result
//			= new TestBaseByteBufferReader(true).read(new UnpooledByteBufAllocator(true).buffer());
//		assertThat(result).isEmpty();
//	}
//
//	@Test
//	void shouldCheckReading() {
//		Optional<Message> result
//			= new TestBaseByteBufferReader(false).read(new UnpooledByteBufAllocator(true).buffer());
//		assertThat(result).isPresent();
//	}
//
//	@RequiredArgsConstructor
//	private static class TestBaseByteBufferReader extends BaseByteBufferReader<Message> {
//		private final boolean doThrow;
//
//		@Override
//		protected Optional<Message> readUnsafe(ByteBuf buffer) {
//			if (doThrow){
//				throw new IndexOutOfBoundsException("");
//			}
//			return Optional.of(new TestConfirmationMessage(null, null, null));
//		}
//	}
//}
