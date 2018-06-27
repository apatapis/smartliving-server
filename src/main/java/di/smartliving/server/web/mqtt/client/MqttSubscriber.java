package di.smartliving.server.web.mqtt.client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttSubscriber {

	private MqttClient mqttClient;

	public MqttSubscriber(String brokerUrl, SubscriberMqttCallback subscriberMqttCallback) {
		try {
			mqttClient = new MqttClient(brokerUrl, MqttClient.generateClientId());
			mqttClient.setCallback(subscriberMqttCallback);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public String getClientId() {
		return mqttClient.getClientId();
	}

	public void start(String topic) {
		try {
			MqttConnectOptions opts = new MqttConnectOptions();
			opts.setCleanSession(true);
			mqttClient.connect(opts);
			mqttClient.subscribe(topic);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		System.out.println("Subscriber " + getClientId() + " is now listening to topic " + topic + ".");
	}

	public void stop() {
		try {
			if (mqttClient.isConnected()) {
				mqttClient.disconnect();
			}
		} catch (MqttException e) {
			e.printStackTrace();
		}
		System.out.println("Subscriber " + getClientId() + " shut down successfully.");
	}
}
