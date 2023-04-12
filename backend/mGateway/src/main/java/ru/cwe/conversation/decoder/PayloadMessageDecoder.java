package ru.cwe.conversation.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.reader.buffer.ByteBufferReader;
import ru.cwe.conversation.message.Message;
import ru.cwe.conversation.reader.buffer.PayloadByteBufferReader;

import java.util.List;
import java.util.Optional;

public final class PayloadMessageDecoder extends ReplayingDecoder<Message> {
	private final ByteBufferReader<PayloadMessage> payloadReader;

	public static Builder builder(){
		return new Builder();
	}

	public static PayloadMessageDecoder instance(){
		return builder().build();
	}

	private PayloadMessageDecoder(final ByteBufferReader<PayloadMessage> payloadReader) {
		this.payloadReader = payloadReader;
	}

	@Override
	protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) throws Exception {
		Optional<?> readingResult = payloadReader.read(in);
		readingResult.ifPresent(out::add);
	}

	public static class Builder {
		private ByteBufferReader<PayloadMessage> payloadReader;

		public Builder payload(final ByteBufferReader<PayloadMessage> payloadReader){
			this.payloadReader = payloadReader;
			return this;
		}

		public PayloadMessageDecoder build(){
			return new PayloadMessageDecoder(
				payloadReader != null ? payloadReader : PayloadByteBufferReader.instance()
			);
		}
	}
}
