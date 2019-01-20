package di.smartliving.server.service;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import di.smartliving.server.dto.SensorMessage;
import di.smartliving.server.util.DtoMapper;
import di.smartliving.server.util.exception.CreateSensorMessageException;

@Service
public class SensorService {

	private static Logger LOGGER = LoggerFactory.getLogger(SensorService.class);

	@Autowired
	private EventService eventService;

	/**
	 * The incoming sensor message is decoded and pushed to the Event service.
	 * 
	 * @param topic
	 * @param sensorMessagePayloadAsString
	 * @param timestamp
	 */
	public void handleIncomingMessage(String topic, String sensorMessagePayloadAsString, Instant timestamp) {
		SensorMessage sensorMessage;
		try {
			sensorMessage = DtoMapper.from(topic, sensorMessagePayloadAsString, timestamp);
		} catch (CreateSensorMessageException e) {
			LOGGER.error("Unable to create SensorMessage from [mqttTopic = {}, mqttMessagePayload = {}].", topic,
					sensorMessagePayloadAsString);
			return;
		}
		eventService.addValueEvent(sensorMessage);
	}
}
