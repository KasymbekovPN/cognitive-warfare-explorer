package utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.cwe.conversation.type.MessageType;

@EqualsAndHashCode
@Getter
public final class TestMessageType implements MessageType {
	private static final String DEFAULT_TYPE = "default.type";

	private final String type;

	public TestMessageType(String type) {
		this.type = type;
	}

	public TestMessageType() {
		this.type = DEFAULT_TYPE;
	}
}
