package ru.cwe.conversation.tube.listener;

import ru.cwe.conversation.address.Address;
import ru.cwe.conversation.tube.datum.TubeDatum;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class FromEndpointTubeListener implements TubeListener {
	private final Map<Address, Integer> counters = new HashMap<>();

	public synchronized Map<Address, Integer> getCounters(){
		return Collections.unmodifiableMap(counters);
	}

	public synchronized Integer getCounter(Address address){
		return counters.getOrDefault(address, 0);
	}

	@Override
	public synchronized void listen(TubeDatum datum) {
		Address from = datum.getMessage().getFrom();
		this.counters.put(from, counters.getOrDefault(from, 0) + 1);
	}
}
