package ru.cwe.conversation.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.address.AddressBuilder;
import ru.cwe.conversation.message.Message;
import ru.cwe.conversation.message.MessageBuilder;
import ru.cwe.conversation.type.MessageTypeBuilder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public final class MessageDecoder extends ReplayingDecoder<Message> {
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		MessageBuilder builder = new MessageBuilder();

		if (in.readBoolean()){ builder.response(); }
		else { builder.request(); }
		builder.uuid(readUuid(in));
		builder.type(new MessageTypeBuilder().type(readString(in)).build());
		builder.content(readString(in));
		builder.from(readAddress(in));
		builder.to(readAddress(in));

		out.add(builder.build());
	}

	private UUID readUuid(ByteBuf in) {
		long low = in.readLong();
		long high = in.readLong();
		return new UUID(high, low);
	}

	private String readString(ByteBuf in) {
		int len = in.readInt();
		return in.readCharSequence(len, CHARSET).toString();
	}

	private Address readAddress(ByteBuf in) {
		return new AddressBuilder()
			.host(readString(in))
			.port(in.readInt())
			.build();
	}
}
