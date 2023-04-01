package utils.faker;

import lombok.RequiredArgsConstructor;
import ru.cwe.conversation.message.Priorities;
import ru.cwe.conversation.message.Versions;
import ru.cwe.conversation.message.confirmation.ConfirmationResult;

@RequiredArgsConstructor
public final class MessageFaker {
	private final BaseFaker base;

	public int version(){
		return base.numberBetween(Versions.MIN, Versions.MAX + 1);
	}

	public int priority(){
		return base.numberBetween(Priorities.MIN, Priorities.MAX + 1);
	}

	public ConfirmationResult confirmationResult(){
		return ConfirmationResult.valueOf(
			base.numberBetween(
				ConfirmationResult.INVALID.getValue(),
				ConfirmationResult.RESPONSE.getValue() + 1)
		);
	}
}
