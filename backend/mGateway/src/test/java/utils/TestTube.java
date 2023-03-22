package utils;

import lombok.Getter;
import ru.cwe.conversation.Tube;
import ru.cwe.conversation.message.Message;

import java.util.ArrayList;
import java.util.List;

public final class TestTube implements Tube {
	@Getter
	private final List<Message> messages = new ArrayList<>();

	@Override
	public void append(Message message) {
		messages.add(message);
	}
}
