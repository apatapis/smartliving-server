package di.smartliving.server.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import di.smartliving.server.service.exception.CreateSubscriberException;
import di.smartliving.server.service.exception.StartSubscriberException;
import di.smartliving.server.service.exception.StopSubscriberException;
import di.smartliving.server.web.mqtt.client.MqttSubscriber;
import di.smartliving.server.web.mqtt.client.MqttSubscriberFactory;

/**
 * Handles all operations related to the application's MQTT subscribers.
 * 
 */
@Service
public class SubscriberService {

	private static Logger LOGGER = LoggerFactory.getLogger(SubscriberService.class);

	@Autowired
	private ConcurrentHashMap<String, Set<MqttSubscriber>> subscriberMap;

	@Autowired
	private MqttSubscriberFactory subscriberFactory;

	public void createSubscribers(String storageUnitId, Set<String> topics) {
		if (subscriberMap.containsKey(storageUnitId)) {
			LOGGER.error("Subscribers for storage unit {} already exist.", storageUnitId);
			return;
		}
		Set<MqttSubscriber> subscribers = new HashSet<>();
		for (String topic : topics) {
			try {
				subscribers.add(subscriberFactory.create(topic));
			} catch (MqttException e) {
				LOGGER.error("Failed to create subscriber for topic {}. Aborting operation.", topic, e);
				throw new CreateSubscriberException();
			}
		}
		subscriberMap.put(storageUnitId, subscribers);
	}
	
	public Set<MqttSubscriber> getSubscribers(String storageUnitId) {
		return subscriberMap.getOrDefault(storageUnitId, Collections.emptySet());
	}
	
	public Map<String, Set<MqttSubscriber>> getSubscriberMap() {
		return subscriberMap;
	}

	public void deleteSubscribers(String storageUnitId) {
		Set<MqttSubscriber> subscribers = subscriberMap.get(storageUnitId);
		if (subscribers == null) {
			LOGGER.error("Subscribers for storage unit {} not found.", storageUnitId);
			return;
		}
		stopSubscribers(subscribers);
		subscriberMap.remove(storageUnitId);
	}

	public void startSubscribers(String storageUnitId) {
		Set<MqttSubscriber> subscribers = subscriberMap.get(storageUnitId);
		if (subscribers == null) {
			LOGGER.error("Subscribers for storage unit {} not found.", storageUnitId);
			return;
		}
		for (MqttSubscriber subscriber : subscribers) {
			try {
				subscriber.start();
			} catch (MqttException e) {
				LOGGER.error("Failed to start {}. Aborting operation.", subscriber, e);
				throw new StartSubscriberException();
			}
		}
	}

	public void stopSubscribers(String storageUnitId) {
		Set<MqttSubscriber> subscribers = subscriberMap.get(storageUnitId);
		if (subscribers == null) {
			LOGGER.error("Subscribers for storage unit {} not found.", storageUnitId);
			return;
		}
		stopSubscribers(subscribers);
	}

	private void stopSubscribers(Set<MqttSubscriber> subscribers) {
		for (MqttSubscriber subscriber : subscribers) {
			try {
				subscriber.stop();
			} catch (MqttException e) {
				LOGGER.error("Failed to stop {}. Aborting operation.", subscriber, e);
				throw new StopSubscriberException();
			}
		}
	}
}
