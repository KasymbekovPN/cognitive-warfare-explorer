package ru.cwe.conversation.gateway.out;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.gateway.BootstrapHolder;
import ru.cwe.conversation.gateway.in.FutureProcessor;
import ru.cwe.conversation.message.payload.PayloadMessage;

@RequiredArgsConstructor
public class OutGatewayImpl implements OutGateway {
	private final BootstrapHolder bootstrapHolder;
	private final FutureProcessor futureProcessor;

	@Override
	public void send(PayloadMessage message) {
		bootstrapHolder.getBootstrap().handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// TODO: 08.04.2023 impl
			}
		});

//		try{
//			futureProcessor.process(bootstrapHolder.getFuture());
//		} catch (Throwable ignored){}
//		finally {
//			bootstrapHolder.shutdown();
//		}
	}

	@Override
	public void shutdown() {
		// TODO: 08.04.2023 impl
	}
}

/*

package org.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 8080;
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        NewClientHandler clientHandler = new NewClientHandler();

        System.out.println("run 0");
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(
                        new RequestDataEncoder(),
                        new ResponseDataDecoder(),
                        new ClientHandler()
                );
            }
        });

        int size = 10;
        try{
            for (int i = 0; i < size; i++) {
                System.out.println("run 1");
                ChannelFuture future = bootstrap.connect(host, port).sync();
                System.out.println("run 2");
                future.channel().closeFuture().sync();
                System.out.println("run 3");
            }
        } finally {
            workGroup.shutdownGracefully();
        }
    }

    public static class NewClientHandler extends ChannelInboundHandlerAdapter {
        private int counter = 100;

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            RequestData requestData = new RequestData();
            requestData.setIntValue(counter++);
            requestData.setStringValue("all work and no play makes jack a dull boy");

            ChannelFuture future = ctx.writeAndFlush(requestData);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println((ResponseData) msg);
            ctx.close();
        }
    }
}

 */
