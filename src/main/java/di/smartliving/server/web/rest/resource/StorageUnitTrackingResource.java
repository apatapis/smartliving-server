package di.smartliving.server.web.rest.resource;

import di.smartliving.server.dto.SubscriptionStrategy;

public class StorageUnitTrackingResource {

	private boolean tracking;
	private SubscriptionStrategy subscriptionStrategy;

	public boolean getTracking() {
		return tracking;
	}

	public void setTracking(boolean tracking) {
		this.tracking = tracking;
	}

	public SubscriptionStrategy getSubscriptionStrategy() {
		return subscriptionStrategy;
	}

	public void setSubscriptionStrategy(SubscriptionStrategy subscriptionStrategy) {
		this.subscriptionStrategy = subscriptionStrategy;
	}

}
