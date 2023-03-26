package ru.cwe.conversation.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.buffer.reader.ByteBufferReader;
import ru.cwe.conversation.message.Message;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public final class MessageDecoder extends ReplayingDecoder<Message> {
	private final ByteBufferReader<Message> confirmationReader;
	private final ByteBufferReader<Message> payloadReader;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		Optional<Message> readingResult = confirmationReader.read(in);
		if (readingResult.isEmpty()){
			readingResult = payloadReader.read(in);
		}
		readingResult.ifPresent(out::add);
	}
}
