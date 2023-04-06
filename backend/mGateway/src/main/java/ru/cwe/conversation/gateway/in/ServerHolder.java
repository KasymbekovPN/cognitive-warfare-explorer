package ru.cwe.conversation.gateway.in;

import io.netty.channel.ChannelFuture;

// TODO: 06.04.2023 del
//            ServerBootstrap bootstrap = new ServerBootstrap();
//            bootstrap
//                    .group(bossGroup, workGroup) // todo test
//                    .channel(NioServerSocketChannel.class)// todo test
//                    .childHandler(new ChannelInitializer<SocketChannel>() {// todo test
//                        @Override
//                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(
//                                    new RequestDecoder(),
//                                    new ResponseDataEncoder(),
//                                    new ProcessingHandler()
//                            );
//                        }
//                    }).option(ChannelOption.SO_BACKLOG, 128)// todo test
//                    .childOption(ChannelOption.SO_KEEPALIVE, true);// todo test
//            ChannelFuture future = bootstrap.bind(port).sync();// todo test
//            future.channel().closeFuture().sync();// todo test

public interface ServerHolder {
	void shutdown();
	ChannelFuture getFuture();
}
