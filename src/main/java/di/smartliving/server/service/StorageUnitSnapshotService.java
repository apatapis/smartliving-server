package di.smartliving.server.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import di.smartliving.server.domain.Container;
import di.smartliving.server.domain.Event;
import di.smartliving.server.domain.StorageUnit;
import di.smartliving.server.web.rest.exception.RestException;


@Service
public class StorageUnitSnapshotService {

	private static Logger LOGGER = LoggerFactory.getLogger(StorageUnitSnapshotService.class);

	@Autowired
	private StorageUnitCRUDService storageUnitCRUDService;

	@Autowired
	private EventService eventService;

	/**
	 * Retrieves latest values for the containers of the specified storage unit.
	 * 
	 * @param storageUnitId
	 * @return
	 */
	public Pair<StorageUnit, Map<Container.ID, BigDecimal>> getSnapshot(Long storageUnitId) {
		
		StorageUnit storageUnit = storageUnitCRUDService.getStorageUnit(storageUnitId).orElseThrow(() -> RestException
				.create(HttpStatus.NOT_FOUND, "Storage unit with id " + storageUnitId + " not found."));
		
		if (!storageUnit.isEnabled()) {
			throw RestException.create(HttpStatus.BAD_REQUEST, "Storage unit " + storageUnitId + " is not enabled.");
		}
		
		List<Container.ID> containerIds = storageUnit.getContainers()
				   .stream()
				   .map(Container::getId)
				   .collect(Collectors.toList());
		
		Map<Container.ID, Event> latestValueEvents = eventService.findLatestValueEvents(containerIds);
		
		Map<Container.ID, BigDecimal> containerValues = new HashMap<>();
		for (Map.Entry<Container.ID, Event> entry : latestValueEvents.entrySet()) {
			containerValues.put(entry.getKey(), entry.getValue().getValue());
		}
//		latestValueEvents.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey(), entry -> {
//			return entry.getValue() != null ? entry.getValue().getValue() : BigDecimal.ZERO;
//		}));
		
		return Pair.of(storageUnit, containerValues);
	}
	
}
