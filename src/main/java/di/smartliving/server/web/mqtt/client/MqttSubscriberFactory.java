package di.smartliving.server.web.mqtt.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttSubscriberFactory {

	@Autowired
	private SubscriberMqttCallbackFactory subscriberMqttCallbackFactory;

	public MqttSubscriber create(String brokerUrl) {
		return new MqttSubscriber(brokerUrl, subscriberMqttCallbackFactory.create());
	};
}
