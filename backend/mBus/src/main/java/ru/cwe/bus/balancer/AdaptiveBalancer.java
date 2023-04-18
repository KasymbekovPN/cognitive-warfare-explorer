package ru.cwe.bus.balancer;

import ru.cwe.conversation.message.payload.PayloadMessage;
import ru.cwe.conversation.tube.TubeOld;
import ru.cwe.conversation.tube.TubeDatumImpl;

import java.util.HashMap;
import java.util.Map;

public final class AdaptiveBalancer implements Balancer {
	private final Map<Integer, TubeOld> tubes;

	public static Builder builder() {
		return new Builder();
	}

	private AdaptiveBalancer(Map<Integer, TubeOld> tubes) {
		this.tubes = tubes;
	}

	@Override
	public void balance(final PayloadMessage message) {
		int min = Integer.MAX_VALUE;
		int index = -1;
		for (Map.Entry<Integer, TubeOld> entry : tubes.entrySet()) {
			if (min > entry.getValue().size()){
				min = entry.getValue().size();
				index = entry.getKey();
			}
		}

		tubes.get(index).put(new TubeDatumImpl(message));
	}

	public static class Builder {
		private final Map<Integer, TubeOld> tubes = new HashMap<>();

		private int counter = 0;

		public Builder tube(TubeOld tubeOld) {
			tubes.put(counter++, tubeOld);
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
