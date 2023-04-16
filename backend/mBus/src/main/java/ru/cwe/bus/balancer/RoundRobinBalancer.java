package ru.cwe.bus.balancer;

import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.tube.Tube;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class RoundRobinBalancer implements Balancer {
	private final AtomicInteger counter = new AtomicInteger(0);
	private final Map<Integer, Tube> tubes;

	public static Builder builder(){
		return new Builder();
	}

	private RoundRobinBalancer(Map<Integer, Tube> tubes) {
		this.tubes = tubes;
	}

	@Override
	public void balance(final PayloadMessage message) {
		int index = counter.getAndIncrement() % tubes.size();
		this.tubes.get(index).send(message);
	}

	public static class Builder {
		private final Map<Integer, Tube> tubes = new HashMap<>();

		private int counter = 0;

		public Builder tube(Tube tube) {
			tubes.put(counter++, tube);
			return this;
		}

		public RoundRobinBalancer build(){
			check();
			return new RoundRobinBalancer(tubes);
		}

		private void check() {
			if (tubes.isEmpty()){
				throw new BalancerBuilderException("No one tube");
			}
		}
	}
}
