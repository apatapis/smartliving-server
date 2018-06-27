package di.smartliving.server.web.mqtt.client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import di.smartliving.server.domain.Message;
import di.smartliving.server.domain.repository.MessageRepository;
import di.smartliving.server.global.StateManager;
import di.smartliving.server.web.mqtt.dto.SensorMessage;

public class SubscriberMqttCallback implements MqttCallback {

	private MessageRepository messageRepository;
	private StateManager stateManager;

	public SubscriberMqttCallback(MessageRepository messageRepository, StateManager stateManager) {
		super();
		this.messageRepository = messageRepository;
		this.stateManager = stateManager;
	}

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
		SensorMessage sensorMessage = new ObjectMapper().readValue(new String(msg.getPayload()), SensorMessage.class);
		System.out.println("Received message with ID '" + msg.getId() + "' for topic '" + topic + "' and content '"
				+ sensorMessage + "'");
		messageRepository.save(Message.from(stateManager.getActiveProfile(), topic, sensorMessage));
	}
}