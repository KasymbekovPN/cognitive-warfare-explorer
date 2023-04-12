package ru.cwe.conversation.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import ru.cwe.conversation.message.Message;
import ru.cwe.conversation.message.confirmation.ConfirmationMessage;
import ru.cwe.conversation.reader.buffer.ByteBufferReader;
import ru.cwe.conversation.reader.buffer.ConfirmationByteBufferReader;

import java.util.List;
import java.util.Optional;

public final class ConfirmationMessageDecoder extends ReplayingDecoder<Message> {
	private final ByteBufferReader<ConfirmationMessage> confirmationReader;

	public static Builder builder() {
		return new Builder();
	}

	public static ConfirmationMessageDecoder instance(){
		return builder().build();
	}

	private ConfirmationMessageDecoder(ByteBufferReader<ConfirmationMessage> confirmationReader) {
		this.confirmationReader = confirmationReader;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		Optional<ConfirmationMessage> maybe = confirmationReader.read(in);
		maybe.ifPresent(out::add);
	}

	public static class Builder {
		private ByteBufferReader<ConfirmationMessage> confirmationReader;

		public Builder confirmation(final ByteBufferReader<ConfirmationMessage> confirmationReader){
			this.confirmationReader = confirmationReader;
			return this;
		}

		public ConfirmationMessageDecoder build(){
			return new ConfirmationMessageDecoder(
				confirmationReader != null ? confirmationReader : ConfirmationByteBufferReader.instance()
			);
		}
	}
}
