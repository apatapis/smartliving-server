package di.smartliving.server.web.mqtt.client;

import java.time.Instant;
import java.util.Date;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import di.smartliving.server.service.SensorService;

@Component
public class MqttSubscriberCallback implements MqttCallback {

	private static Logger LOGGER = LoggerFactory.getLogger(MqttSubscriberCallback.class);

	private final SensorService sensorService;

	public MqttSubscriberCallback(SensorService sensorService) {
		this.sensorService = sensorService;
	}

	@Override
	public void connectionLost(Throwable throwable) {
		LOGGER.error("Connection lost.", throwable);
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {

	}

	@Override
	public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
		String payloadAsString = new String(mqttMessage.getPayload());
		LOGGER.info("Received message {} for topic {} with payload {}.", mqttMessage.getId(), topic, payloadAsString);
		sensorService.handleIncomingMessage(topic, payloadAsString, Date.from(Instant.now()));
	}
}