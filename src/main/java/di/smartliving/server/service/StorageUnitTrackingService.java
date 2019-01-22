package di.smartliving.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import di.smartliving.server.domain.StorageUnit;
import di.smartliving.server.dto.SubscriptionStrategy;
import di.smartliving.server.util.SubscriptionHelper;
import di.smartliving.server.web.rest.exception.RestException;

/**
 * Handles tracking of storage units using MQTT subscribers.
 * 
 */
@Service
public class StorageUnitTrackingService {

	private static Logger LOGGER = LoggerFactory.getLogger(StorageUnitTrackingService.class);

	@Autowired
	private StorageUnitCRUDService storageUnitCRUDService;

	@Autowired
	private SubscriberService subscriberService;

	/**
	 * Creates and starts subscribers for the specified storage unit using the
	 * provided subscription strategy.
	 * 
	 * @param storageUnitId
	 * @param subscriptionStrategy
	 */
	public void track(Long storageUnitId, SubscriptionStrategy subscriptionStrategy) {
		StorageUnit storageUnit = storageUnitCRUDService.getStorageUnit(storageUnitId).orElseThrow(() -> RestException
				.create(HttpStatus.NOT_FOUND, "Storage unit with id " + storageUnitId + " not found."));

		if (!storageUnit.isEnabled()) {
			throw RestException.create(HttpStatus.BAD_REQUEST, "Storage unit " + storageUnitId + " is not enabled.");
		}

		subscriberService.createSubscribers(storageUnitId,
				SubscriptionHelper.getTopics(storageUnit, subscriptionStrategy));
		subscriberService.startSubscribers(storageUnitId);
	}

	/**
	 * Stops tracking specified storage unit.
	 * 
	 * @param storageUnitId
	 */
	public void stopTracking(Long storageUnitId) {
		subscriberService.stopSubscribers(storageUnitId);
	}
}
