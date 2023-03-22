package ru.cwe.conversation.processing;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.cwe.conversation.Tube;
import ru.cwe.conversation.message.Message;

import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
public final class ServerSideProcessing extends ChannelInboundHandlerAdapter {
	private final Tube requestTube;
	private final Tube responseTube;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		CheckingResult result = new TypeChecker().apply(msg);
		if (result.isSuccess()){
			Message message = (Message) msg;
			if (message.isResponse()){
				requestTube.append(message);
			} else {
				responseTube.append(message);
			}
			return;
		}
		log.warn("Message has wrong type ({}); must be Message implementation", result.getName());
	}

	@RequiredArgsConstructor
	@Getter
	public static class CheckingResult {
		private final boolean success;
		private final String name;
	}

	public static class TypeChecker implements Function<Object, CheckingResult>{
		@Override
		public CheckingResult apply(Object o) {
			Class<?> type = o.getClass();
			return new CheckingResult(
				Message.class.isAssignableFrom(type),
				type.getSimpleName()
			);
		}
	}
}
