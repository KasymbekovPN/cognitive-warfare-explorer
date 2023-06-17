package ru.cwe.common.listener.api.factory;

import ru.cwe.common.listener.api.Listener;

public interface ListenerFactory<T extends Listener> {
	T create();
}
