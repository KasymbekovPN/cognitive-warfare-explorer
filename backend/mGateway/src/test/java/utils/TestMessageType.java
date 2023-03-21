package utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.cwe.conversation.type.MessageType;

@EqualsAndHashCode
@Getter
public final class TestMessageType implements MessageType {
	private static final String DEFAULT_NAME = "default.name";

	private final String name;

	public TestMessageType(String name) {
		this.name = name;
	}

	public TestMessageType() {
		this.name = DEFAULT_NAME;
	}
}
