package di.smartliving.server.web.mqtt.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import di.smartliving.server.domain.repository.MessageRepository;
import di.smartliving.server.global.StateManager;

@Component
public class SubscriberMqttCallbackFactory {

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private StateManager stateManager;

	public SubscriberMqttCallback create() {
		return new SubscriberMqttCallback(messageRepository, stateManager);
	};
}
