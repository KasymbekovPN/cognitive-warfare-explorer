package ru.cwe.bus.balancer;

import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.tube.Tube;

import java.util.HashMap;
import java.util.Map;

public final class AdaptiveBalancer implements Balancer {
	private final Map<Integer, Tube> tubes;

	public static Builder builder() {
		return new Builder();
	}

	private AdaptiveBalancer(Map<Integer, Tube> tubes) {
		this.tubes = tubes;
	}

	@Override
	public void balance(final PayloadMessage message) {
		int min = Integer.MAX_VALUE;
		int index = -1;
		for (Map.Entry<Integer, Tube> entry : tubes.entrySet()) {
			if (min > entry.getValue().size()){
				min = entry.getValue().size();
				index = entry.getKey();
			}
		}

		tubes.get(index).send(message);
	}

	public static class Builder {
		private final Map<Integer, Tube> tubes = new HashMap<>();

		private int counter = 0;

		public Builder tube(Tube tube) {
			tubes.put(counter++, tube);
			return this;
		}

		public AdaptiveBalancer build(){
			check();
			return new AdaptiveBalancer(tubes);
		}

		private void check() {
			if (tubes.isEmpty()){
				throw new BalancerBuilderException("No one tube");
			}
		}
	}
}
