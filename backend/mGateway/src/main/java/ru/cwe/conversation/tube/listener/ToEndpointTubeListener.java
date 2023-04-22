package ru.cwe.conversation.tube.listener;

import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.tube.datum.TubeDatum;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ToEndpointTubeListener implements TubeListener {
	private final Map<Address, Integer> counters = new HashMap<>();

	public synchronized Map<Address, Integer> getCounters() {
		return Collections.unmodifiableMap(counters);
	}

	public synchronized Integer getCounter(Address address){
		return counters.getOrDefault(address, 0);
	}

	@Override
	public synchronized void listen(TubeDatum datum) {
		Address to = datum.getMessage().getTo();
		this.counters.put(to, counters.getOrDefault(to, 0) + 1);
	}
}
