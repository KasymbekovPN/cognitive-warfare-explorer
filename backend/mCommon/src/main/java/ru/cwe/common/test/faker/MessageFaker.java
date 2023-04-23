package ru.cwe.common.test.faker;

import lombok.RequiredArgsConstructor;

// TODO: 23.04.2023 add message builders
// TODO: 23.04.2023 impl it
@RequiredArgsConstructor
public final class MessageFaker {
	private final BaseFaker base;



	/*

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

	 */
}
