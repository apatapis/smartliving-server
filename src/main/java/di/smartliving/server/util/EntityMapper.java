package di.smartliving.server.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import di.smartliving.server.domain.Container;
import di.smartliving.server.domain.Event;
import di.smartliving.server.domain.EventType;
import di.smartliving.server.domain.StorageUnit;
import di.smartliving.server.dto.SensorMessage;
import di.smartliving.server.web.rest.resource.ContainerResource;
import di.smartliving.server.web.rest.resource.StorageUnitResource;

public class EntityMapper {

	private EntityMapper() {
	};

	public static Container from(ContainerResource containerResource, Long position, Long level, StorageUnit storageUnit) {
		Container container = new Container();
		container.setId(new Container.ID(storageUnit.getId(), level, position));
		container.setStorageUnit(storageUnit);
		container.setName(containerResource.getName());
		container.setTareWeight(containerResource.getTareWeight());
		container.setThresholdMax(containerResource.getThresholdMax());
		container.setThresholdMin(containerResource.getThresholdMin());
		container.setUnit(containerResource.getUnit());
		return container;
	}

	public static StorageUnit from(StorageUnitResource storageUnitResource) {
		StorageUnit storageUnit = new StorageUnit();
		storageUnit.setId(storageUnitResource.getId());
		storageUnit.setName(storageUnitResource.getName());
		storageUnit.setEnabled(storageUnitResource.isEnabled());
		storageUnit.setContainers(from(storageUnitResource.getContainers(), storageUnit));
		return storageUnit;
	}
	
	private static List<Container> from(List<List<ContainerResource>> containerResources, StorageUnit storageUnit) {
		return IntStream.range(0, containerResources.size())
						.mapToObj(level -> IntStream.range(0, containerResources.get(level).size())
													.mapToObj(position -> from(containerResources.get(level).get(position),
																				Long.valueOf(position),
																				Long.valueOf(level),
																				storageUnit)))
						.flatMap(Function.identity())
						.collect(Collectors.toList());
	}

	public static Event from(SensorMessage sensorMessage) {
		Event event = new Event();
		event.setContainerId(sensorMessage.getContainerId());
		event.setType(EventType.VALUE);
		event.setValue(sensorMessage.getValue());
		event.setCreatedDate(sensorMessage.getTimestamp());
		return event;
	}
}
