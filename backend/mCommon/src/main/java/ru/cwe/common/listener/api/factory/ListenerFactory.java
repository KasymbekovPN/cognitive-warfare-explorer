package ru.cwe.common.listener.api.factory;

import ru.cwe.common.listener.api.listener.Listener;

public interface ListenerFactory<T extends Listener> {
	T create();
}
