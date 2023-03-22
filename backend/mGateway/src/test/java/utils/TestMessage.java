package utils;

import lombok.Getter;
import lombok.Setter;
import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.message.Message;
import ru.cwe.conversation.type.MessageType;

import java.util.UUID;

@Getter
@Setter
public final class TestMessage implements Message {
	private boolean response;
	private UUID uuid;
	private MessageType type;
	private String content;
	private Address from;
	private Address to;
}
