package ru.cwe.conversation.exception;

import java.util.Optional;

public interface RuntimeExceptionBuilder {
	Optional<RuntimeException> build();
}
