package ru.cwe.conversation.gateway.in;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import ru.cwe.conversation.address.AddressBuilder;
import ru.cwe.conversation.container.MessageContainer;
import ru.cwe.conversation.converter.PayloadToConfirmationMessageConverter;
import ru.cwe.conversation.converter.ToPayloadMessageConverter;
import ru.cwe.conversation.decoder.MessageDecoder;
import ru.cwe.conversation.encoder.MessageEncoder;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.processing.ServerMessageReceiver;
import ru.cwe.conversation.reader.buffer.ConfirmationByteBufferReader;
import ru.cwe.conversation.reader.buffer.PayloadByteBufferReader;
import ru.cwe.conversation.reader.value.*;
import ru.cwe.conversation.writer.buffer.ConfirmationByteBufferWriter;
import ru.cwe.conversation.writer.buffer.PayloadByteBufferWriter;
import ru.cwe.conversation.writer.value.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class InFactoryImpl implements InFactory {
	private static final Charset CHARSET = StandardCharsets.UTF_8;

	@Override
	public InGateway create(MessageContainer<PayloadMessage> requestMessageContainer,
							MessageContainer<PayloadMessage> responseMessageContainer,
							int port) {

		ConfirmationByteBufferReader confirmationByteBufferReader = new ConfirmationByteBufferReader(
			new ConfirmationHeaderByteBufferValueReader(),
			new UuidByteBufferValueReader(),
			new StringByteBufferValueReader(CHARSET)
		);

		PayloadByteBufferReader payloadByteBufferReader = new PayloadByteBufferReader(
			new PayloadHeaderByteBufferValueReader(),
			new UuidByteBufferValueReader(),
			new StringByteBufferValueReader(CHARSET),
			new AddressByteBufferValueReader(new StringByteBufferValueReader(CHARSET), AddressBuilder.builder())
		);

		MessageDecoder decoder = new MessageDecoder(confirmationByteBufferReader, payloadByteBufferReader);

		ConfirmationByteBufferWriter confirmationByteBufferWriter = new ConfirmationByteBufferWriter(
			new ConfirmationHeaderByteBufferValueWriter(),
			new UuidByteBufferValueWriter(),
			new StringByteBufferValueWriter(CHARSET)
		);

		PayloadByteBufferWriter payloadByteBufferWriter = new PayloadByteBufferWriter(
			new PayloadHeaderByteBufferValueWriter(),
			new UuidByteBufferValueWriter(),
			new StringByteBufferValueWriter(CHARSET),
			new AddressByteBufferValueWriter(new StringByteBufferValueWriter(CHARSET))
		);

		MessageEncoder encoder = new MessageEncoder(confirmationByteBufferWriter, payloadByteBufferWriter);

		ServerMessageReceiver receiver = new ServerMessageReceiver(
			requestMessageContainer,
			responseMessageContainer,
			new ToPayloadMessageConverter(),
			new PayloadToConfirmationMessageConverter()
		);

		ServerBootstrap bootstrap = new ServerBootstrap()
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(
						decoder,
						encoder,
						receiver
					);
				}
			})
			.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true);

		ServerHolderImpl holder = ServerHolderImpl.builder().build(bootstrap, port);

		return new InGatewayImpl(holder, new FutureProcessorImpl());
	}
}
