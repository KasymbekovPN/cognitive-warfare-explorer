package ru.cwe.conversation.tube;

import lombok.Getter;
import ru.cwe.conversation.message.payload.PayloadMessage;

@Getter
public final class TubeDatumImpl implements TubeDatum {
	private final PayloadMessage message;
	private final String host;
	private final int port;

	public TubeDatumImpl(PayloadMessage message, String host, Integer port) {
		this.message = message;
		this.host = host != null ? host : message.getTo().getHost();
		this.port = port != null ? port : message.getTo().getPort();
	}

	public TubeDatumImpl(PayloadMessage message) {
		this.message = message;
		this.host = message.getTo().getHost();
		this.port = message.getTo().getPort();
	}
}
