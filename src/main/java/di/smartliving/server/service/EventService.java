package di.smartliving.server.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import di.smartliving.server.domain.Container;
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
				.findTop1ByContainerIdAndTypeOrderByCreatedDateDesc(sensorMessage.getContainerId(), EventType.VALUE)
				.orElse(null);
		if (latestValueEvent != null && latestValueEvent.getValue().compareTo(sensorMessage.getValue()) == 0) {
			LOGGER.info("No change in value ({}) for {}.", latestValueEvent.getValue(), sensorMessage.getContainerId());
			return;
		}
		eventRepository.save(EntityMapper.from(sensorMessage));
	}
	
	/**
	 * Find latest event values for specified containers.
	 * 
	 * @param containerIds
	 * @return
	 */
	public Map<Container.ID, Event> findLatestValueEvents(List<Container.ID> containerIds) {
		return containerIds
				.stream()
				.collect(Collectors.toMap(Function.identity(), 
										  containerId -> eventRepository.findTop1ByContainerIdAndTypeOrderByCreatedDateDesc(containerId, EventType.VALUE).orElseGet(() -> {
											  Event event = new Event();
											  event.setValue(BigDecimal.ZERO);
											  return event;
										  })));
	}
}
