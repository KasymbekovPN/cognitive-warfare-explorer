package ru.cwe.conversation.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.reader.buffer.ByteBufferReader;
import ru.cwe.conversation.message.Message;
import ru.cwe.conversation.reader.buffer.ConfirmationByteBufferReader;
import ru.cwe.conversation.reader.buffer.PayloadByteBufferReader;

import java.util.List;
import java.util.Optional;

public final class MessageDecoder extends ReplayingDecoder<Message> {
	private final ByteBufferReader<ConfirmationMessage> confirmationReader;
	private final ByteBufferReader<PayloadMessage> payloadReader;

	public static Builder builder(){
		return new Builder();
	}

	public static MessageDecoder instance(){
		return builder().build();
	}

	private MessageDecoder(final ByteBufferReader<ConfirmationMessage> confirmationReader,
						   final ByteBufferReader<PayloadMessage> payloadReader) {
		this.confirmationReader = confirmationReader;
		this.payloadReader = payloadReader;
	}

	@Override
	protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) throws Exception {
		Optional<?> readingResult = confirmationReader.read(in);
		if (readingResult.isEmpty()){
			readingResult = payloadReader.read(in);
		}
		readingResult.ifPresent(out::add);
	}

	public static class Builder {
		private ByteBufferReader<ConfirmationMessage> confirmationReader;
		private ByteBufferReader<PayloadMessage> payloadReader;

		public Builder confirmation(final ByteBufferReader<ConfirmationMessage> confirmationReader){
			this.confirmationReader = confirmationReader;
			return this;
		}

		public Builder payload(final ByteBufferReader<PayloadMessage> payloadReader){
			this.payloadReader = payloadReader;
			return this;
		}

		public MessageDecoder build(){
			return new MessageDecoder(
				confirmationReader != null ? confirmationReader : ConfirmationByteBufferReader.instance(),
				payloadReader != null ? payloadReader : PayloadByteBufferReader.instance()
			);
		}
	}
}
