package di.smartliving.server.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import di.smartliving.server.domain.Container;
import di.smartliving.server.domain.Event;
import di.smartliving.server.domain.EventType;
import di.smartliving.server.domain.repository.EventRepository;
import di.smartliving.server.dto.ContainerValueChange;
import di.smartliving.server.util.DtoMapper;

@Service
public class StorageUnitStatsService {

	private static Logger LOGGER = LoggerFactory.getLogger(StorageUnitStatsService.class);

	@Autowired
	private EventRepository eventRepository;

	public Page<ContainerValueChange> getContainerValueChanges(Container.ID containerId, Pageable pageable) {
		return eventRepository.findByContainerIdAndType(containerId, EventType.VALUE, pageable).map(DtoMapper::from);
	}

	public BigDecimal getContainerAverage(Container.ID containerId) {
		List<Event> events = eventRepository.findByContainerIdAndType(containerId, EventType.VALUE);
		if (events.isEmpty()) {
			return BigDecimal.ZERO;
		}
		return events.stream().map(Event::getValue).reduce(BigDecimal.ZERO, (v1, v2) -> v1.add(v2))
				.divide(new BigDecimal(events.size()));
	}

	public List<ContainerValueChange> getContainerZeros(Container.ID containerId) {
		return eventRepository.findByContainerIdAndType(containerId, EventType.VALUE).stream()
				.filter(event -> BigDecimal.ZERO.compareTo(event.getValue()) == 0).map(DtoMapper::from)
				.collect(Collectors.toList());
	}

}
