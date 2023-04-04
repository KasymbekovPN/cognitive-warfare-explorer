package utils;

import lombok.Getter;
import ru.cwe.conversation.container.Container;
import ru.cwe.conversation.message.payload.PayloadMessage;

import java.util.ArrayList;
import java.util.List;

public final class TestContainer implements Container<PayloadMessage> {
	@Getter
	private final List<PayloadMessage> messages = new ArrayList<>();

	@Override
	public void append(PayloadMessage message) {
		this.messages.add(message);
	}
}
