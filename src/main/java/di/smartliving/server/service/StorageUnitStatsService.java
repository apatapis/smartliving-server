package di.smartliving.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import di.smartliving.server.domain.Container;
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

}
