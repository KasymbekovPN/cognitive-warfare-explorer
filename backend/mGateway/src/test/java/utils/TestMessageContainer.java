package utils;

import lombok.Getter;
import ru.cwe.conversation.container.MessageContainer;

import java.util.ArrayList;
import java.util.List;

public final class TestMessageContainer<M> implements MessageContainer<M> {
	@Getter
	private final List<M> messages = new ArrayList<>();

	@Override
	public void append(M message) {
		this.messages.add(message);
	}
}
