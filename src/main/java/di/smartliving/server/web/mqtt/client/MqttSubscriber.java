package di.smartliving.server.web.mqtt.client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import di.smartliving.server.web.mqtt.dto.SensorMessage;

public class MqttSubscriber {

	private MqttClient mqttClient;

	public MqttSubscriber(String brokerUrl) {
		try {
			mqttClient = new MqttClient(brokerUrl, MqttClient.generateClientId());
			mqttClient.setCallback(new SubscriberMqttCallback());
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

	class SubscriberMqttCallback implements MqttCallback {

		@Override
		public void connectionLost(Throwable arg0) {
			System.out.println("Connection lost");
			arg0.printStackTrace();
		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken arg0) {

		}

		@Override
		public void messageArrived(String topic, MqttMessage msg) throws Exception {
			System.out.println("Received message with ID '" + msg.getId() + "' for topic '" + topic + "' and content '"
					+ new ObjectMapper().readValue(new String(msg.getPayload()), SensorMessage.class) + "'");
		}
	}
}
