package utils.faker;

import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.message.Priorities;
import ru.cwe.conversation.message.Versions;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;

@RequiredArgsConstructor
public final class MessageFaker {
	private final BaseFaker base;

	public int version(){
		return base.number().between(Versions.MIN, Versions.MAX + 1);
	}

	public int priority(){
		return base.number().between(Priorities.MIN, Priorities.MAX + 1);
	}

	public ConfirmationResult confirmationResult(){
		return ConfirmationResult.valueOf(
			base.number().between(
				ConfirmationResult.INVALID.getValue(),
				ConfirmationResult.RESPONSE.getValue() + 1)
		);
	}
}
