package utils.faker;

import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.message.MessageType;
import ru.cwe.conversation.message.Priorities;
import ru.cwe.conversation.message.Versions;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;

// TODO: 22.04.2023 del
@RequiredArgsConstructor
public final class MessageFaker {
	private final BaseFaker base;

	public int version(){
		return base.number().between(Versions.MIN, Versions.MAX + 1);
	}

	public int priority(){
		return base.number().between(Priorities.MIN, Priorities.MAX + 1);
	}

	public MessageType messageType(){
		return MessageType.valueOf(
			base.number().between(
				MessageType.INVALID.getValue(),
				MessageType.CONFIRMATION.getValue() + 1)
		);
	}

	public ConfirmationResult confirmationResult(){
		return ConfirmationResult.valueOf(
			base.number().between(
				ConfirmationResult.INVALID.getValue(),
				ConfirmationResult.RESPONSE.getValue() + 1)
		);
	}
}
