package ru.cwe.conversation.message.confirmation;

import java.util.Optional;

public class ConfirmationMessageBuilderException extends RuntimeException{
	public ConfirmationMessageBuilderException(String message) {
		super(message);
	}

	public static class Builder {
		private final StringBuilder messageSB = new StringBuilder();

		private String delimiter = "Absent fields: ";

		public Builder field(String name, Object value){
			if (value == null){
				messageSB.append(delimiter).append(name);
				delimiter = " & ";
			}
			return this;
		}

		public Optional<ConfirmationMessageBuilderException> build(){
			return messageSB.isEmpty()
				? Optional.empty()
				: Optional.of(new ConfirmationMessageBuilderException(messageSB.toString()));
		}
	}
}
