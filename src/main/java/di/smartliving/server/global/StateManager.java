package di.smartliving.server.global;

import java.util.HashMap;
import java.util.Map;

import di.smartliving.server.dto.ProfileDTO;
import di.smartliving.server.web.mqtt.client.MqttSubscriber;

public class StateManager {

	private ProfileDTO activeProfile;
	private Map<String, MqttSubscriber> subscribers = new HashMap<>();

	public ProfileDTO getActiveProfile() {
		return activeProfile;
	}

	public void setActiveProfile(ProfileDTO activeProfile) {
		this.activeProfile = activeProfile;
	}

	public void clearSubscribers() {
		System.out.println("Cleaning up subscribers...");
		for (MqttSubscriber subscriber : subscribers.values()) {
			subscriber.stop();
		}
		subscribers.clear();
	}

	public void addSubscriber(String topic, MqttSubscriber subscriber) {
		subscribers.put(topic, subscriber);
	}

	public void addSubscribers(Map<String, MqttSubscriber> subscribers) {
		this.subscribers.putAll(subscribers);
	}

	public void startSubscribers() {
		System.out.println("Starting all subscribers...");
		subscribers.entrySet().stream().forEach(entry -> {
			entry.getValue().start(entry.getKey());
		});
	}
}
