// TODO: 30.03.2023 restore
//package ru.cwe.conversation.reader.buffer;
//
//import io.netty.buffer.ByteBuf;
//import lombok.RequiredArgsConstructor;
//import ru.cwe.conversation.address.Address;
//import ru.cwe.conversation.reader.value.ByteBufferValueReader;
//import ru.cwe.conversation.message.MessageType;
//import ru.cwe.conversation.message.payload.PayloadMessage;
//import ru.cwe.conversation.message.payload.PayloadMessageBuilder;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@RequiredArgsConstructor
//public final class PayloadByteBufferReader extends BaseByteBufferReader<PayloadMessage> {
//	private final ByteBufferValueReader<MessageType> typeReader;
//	private final ByteBufferValueReader<UUID> uuidReader;
//	private final ByteBufferValueReader<String> stringReader;
//	private final ByteBufferValueReader<Address> addressReader;
//
//	@Override
//	protected Optional<PayloadMessage> readUnsafe(ByteBuf buffer) {
//		MessageType messageType = typeReader.read(buffer);
//		if (messageType.equals(MessageType.REQUEST) || messageType.equals(MessageType.RESPONSE)){
//			return Optional.of(
//				PayloadMessageBuilder.builder()
//					.type(messageType)
//					.uuid(uuidReader.read(buffer))
//					.contentType(stringReader.read(buffer))
//					.content(stringReader.read(buffer))
//					.from(addressReader.read(buffer))
//					.to(addressReader.read(buffer))
//					.build()
//			);
//		}
//		return Optional.empty();
//	}
//}
