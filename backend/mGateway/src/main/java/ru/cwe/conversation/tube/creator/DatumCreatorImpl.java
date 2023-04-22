package ru.cwe.conversation.tube.creator;

import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.tube.datum.TubeDatumImpl;
import ru.cwe.conversation.tube.TubeOld;

// TODO: 18.04.2023 ???
@RequiredArgsConstructor
public final class DatumCreatorImpl implements DatumCreator {
	private final TubeOld tubeOld;

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
	public TubeOld put() {
		tubeOld.put(new TubeDatumImpl(message, host, port));
		return tubeOld;
	}
}
