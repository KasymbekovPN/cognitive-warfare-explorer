package ru.cwe.common.configuration.api.conf;

import java.util.Optional;
import java.util.function.Supplier;

public interface Config {
	Optional<String> get(String property);
	<T> void extract(Supplier<T> extractor);
}
