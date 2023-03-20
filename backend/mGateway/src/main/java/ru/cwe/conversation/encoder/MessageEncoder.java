package ru.cwe.conversation.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import ru.cwe.conversation.message.Message;
import ru.cwe.conversation.type.MessageType;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public final class MessageEncoder extends MessageToByteEncoder<Message> {
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		out.writeBoolean(msg.isResponse());

		UUID uuid = msg.getUuid();
		out.writeLong(uuid.getLeastSignificantBits());
		out.writeLong(uuid.getMostSignificantBits());

		MessageType type = msg.getType();
		out.writeInt(type.getType().length());
		out.writeCharSequence(type.getType(), CHARSET);

		String content = msg.getContent();
		out.writeInt(content.length());
		out.writeCharSequence(content, CHARSET);

		String fromHost = msg.getFrom().getHost();
		out.writeInt(fromHost.length());
		out.writeCharSequence(fromHost, CHARSET);

		out.writeInt(msg.getFrom().getPort());

		String toHost = msg.getTo().getHost();
		out.writeInt(toHost.length());
		out.writeCharSequence(toHost, CHARSET);

		out.writeInt(msg.getTo().getPort());
	}
}