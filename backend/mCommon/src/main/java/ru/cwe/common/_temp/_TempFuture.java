package ru.cwe.common._temp;

import lombok.Getter;

import java.util.function.Consumer;

// TODO: 07.08.2023 del
public class _TempFuture {
	@Getter private Consumer<Object> success;
	@Getter private Consumer<Throwable> fail;

	public void addCallback(Consumer<Object> success, Consumer<Throwable> fail){
		this.success = success;
		this.fail = fail;
	}
}
