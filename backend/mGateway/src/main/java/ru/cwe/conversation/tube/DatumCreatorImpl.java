package ru.cwe.conversation.tube;

import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.message.payload.PayloadMessage;

@RequiredArgsConstructor
public final class DatumCreatorImpl implements DatumCreator {
	private final Tube tube;

	private PayloadMessage message;
	private String host;
	private Integer port;

	@Override
	public DatumCreator message(PayloadMessage message) {
		this.message = message;
		return this;
	}

	@Override
	public DatumCreator host(String host) {
		this.host  = host;
		return this;
	}

	@Override
	public DatumCreator port(int port) {
		this.port = port;
		return this;
	}

	@Override
	public Tube put() {
		tube.put(new TubeDatumImpl(message, host, port));
		return tube;
	}
}
