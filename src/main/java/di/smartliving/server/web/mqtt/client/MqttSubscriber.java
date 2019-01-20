package di.smartliving.server.web.mqtt.client;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttSubscriber {

	private static final MqttConnectOptions MQTT_CONNECT_OPTIONS = getMqttConnectOptions();

	private static MqttConnectOptions getMqttConnectOptions() {
		MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
		mqttConnectOptions.setCleanSession(true);
		return mqttConnectOptions;
	}

	private final MqttClient mqttClient;
	private final String topic;

	public MqttSubscriber(String brokerUrl, String topic, MqttCallback mqttCallback) throws MqttException {
		this.mqttClient = createMqttClient(brokerUrl, mqttCallback);
		this.topic = topic;
	}

	private MqttClient createMqttClient(String brokerUrl, MqttCallback mqttCallback) throws MqttException {
		MqttClient mqttClient = new MqttClient(brokerUrl, MqttClient.generateClientId());
		mqttClient.setCallback(mqttCallback);
		return mqttClient;
	}

	public String getClientId() {
		return mqttClient.getClientId();
	}

	public String getTopic() {
		return topic;
	}

	public void start() throws MqttException {
		if (mqttClient.isConnected()) {
			return;
		}
		mqttClient.connect(MQTT_CONNECT_OPTIONS);
		mqttClient.subscribe(topic);
	}

	public void stop() throws MqttException {
		if (!mqttClient.isConnected()) {
			return;
		}
		mqttClient.disconnect();
	}

	public boolean isConnected() {
		return mqttClient.isConnected();
	}

	@Override
	public String toString() {
		return "MqttSubscriber [clientId=" + getClientId() + ", topic=" + getTopic() + "]";
	}

}
