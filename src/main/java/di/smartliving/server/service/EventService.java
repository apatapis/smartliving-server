package di.smartliving.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import di.smartliving.server.domain.Event;
import di.smartliving.server.domain.EventType;
import di.smartliving.server.domain.repository.EventRepository;
import di.smartliving.server.dto.SensorMessage;
import di.smartliving.server.util.EntityMapper;

@Service
public class EventService {

	private static Logger LOGGER = LoggerFactory.getLogger(EventService.class);

	@Autowired
	private EventRepository eventRepository;

	/**
	 * The incoming sensor message is translated to a <code>EventType.VALUE</code>
	 * event if the container's value is different from the latest reported one.
	 * 
	 * @param sensorMessage
	 */
	public void addValueEvent(SensorMessage sensorMessage) {
		Event latestValueEvent = eventRepository
				.findTop1ByContainerIdAndTypeOrderByTimestampDesc(sensorMessage.getContainerId(), EventType.VALUE)
				.orElse(null);
		if (latestValueEvent != null && latestValueEvent.getValue().compareTo(sensorMessage.getValue()) == 0) {
			LOGGER.info("No change in value ({}) for {}.", latestValueEvent.getValue(), sensorMessage.getContainerId());
			return;
		}
		eventRepository.save(EntityMapper.from(sensorMessage));
	}
}
