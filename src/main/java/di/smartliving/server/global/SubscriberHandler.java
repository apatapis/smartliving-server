package di.smartliving.server.global;

import java.util.HashMap;
import java.util.Map;

import di.smartliving.server.web.mqtt.client.MqttSubscriber;

public class SubscriberHandler {

	private Map<String, MqttSubscriber> subscribers = new HashMap<>();

	public synchronized void cleanUp() {
		System.out.println("Cleaning up subscribers...");
		for (MqttSubscriber subscriber : subscribers.values()) {
			subscriber.stop();
		}
		subscribers.clear();
	}

	public synchronized void add(String topic, MqttSubscriber subscriber) {
		subscribers.put(topic, subscriber);
	}

	public synchronized void addAll(Map<String, MqttSubscriber> subscribers) {
		this.subscribers.putAll(subscribers);
	}

	public synchronized void startAll() {
		System.out.println("Starting all subscribers...");
		subscribers.entrySet().stream().forEach(entry -> {
			entry.getValue().start(entry.getKey());
		});
	}
}
