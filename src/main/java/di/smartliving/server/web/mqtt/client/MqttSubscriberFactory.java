package di.smartliving.server.web.mqtt.client;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import di.smartliving.server.properties.MqttProperties;

@Component
public class MqttSubscriberFactory {

	@Autowired
	private MqttSubscriberCallback mqttSubscriberCallback;

	@Autowired
	private MqttProperties mqttProperties;

	public MqttSubscriber create(String topic) throws MqttException {
		return new MqttSubscriber(mqttProperties.getBrokerUrl(), topic, mqttSubscriberCallback);
	};
}
