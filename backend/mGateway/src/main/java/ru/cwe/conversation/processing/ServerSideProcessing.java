// TODO: 23.03.2023 ???
//package ru.cwe.conversation.processing;
//
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import ru.cwe.conversation.Tube;
//import ru.cwe.conversation.message.MessageOLd;
//
//import java.util.function.Function;
//
//@RequiredArgsConstructor
//@Slf4j
//public final class ServerSideProcessing extends ChannelInboundHandlerAdapter {
//	private final Tube requestTube;
//	private final Tube responseTube;
//	// TODO: 22.03.2023 add confirmation tube
//
//	@Override
//	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		CheckingResult result = new TypeChecker().apply(msg);
//		if (result.isSuccess()){
//			MessageOLd message = (MessageOLd) msg;
//			if (message.isResponse()){
//				requestTube.append(message);
//			} else {
//				responseTube.append(message);
//			}
//			return;
//		}
//		log.warn("Message has wrong type ({}); must be Message implementation", result.getName());
//
//		// TODO: 22.03.2023 must return little answer (Confirmation) which contains only UUID
//		/*
//
//        ChannelFuture future = ctx.writeAndFlush(responseData);
//        future.addListener(ChannelFutureListener.CLOSE);
//        System.out.println(requestData);
//
//		 */
//	}
//
//	@RequiredArgsConstructor
//	@Getter
//	public static class CheckingResult {
//		private final boolean success;
//		private final String name;
//	}
//
//	public static class TypeChecker implements Function<Object, CheckingResult>{
//		@Override
//		public CheckingResult apply(Object o) {
//			Class<?> type = o.getClass();
//			return new CheckingResult(
//				MessageOLd.class.isAssignableFrom(type),
//				type.getSimpleName()
//			);
//		}
//	}
//}
