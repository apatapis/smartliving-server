package di.smartliving.server.util;

import static java.util.stream.Collectors.groupingBy;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import di.smartliving.server.domain.Container;
import di.smartliving.server.domain.StorageUnit;
import di.smartliving.server.web.mqtt.client.MqttSubscriber;
import di.smartliving.server.web.rest.resource.ContainerResource;
import di.smartliving.server.web.rest.resource.ContainerSnapshotResource;
import di.smartliving.server.web.rest.resource.StorageUnitResource;
import di.smartliving.server.web.rest.resource.StorageUnitSnapshotResource;
import di.smartliving.server.web.rest.resource.SubscriberResource;

public class ResourceMapper {

	private ResourceMapper() {
	};

	public static ContainerResource from(Container container) {
		ContainerResource containerResource = new ContainerResource();
		containerResource.setName(container.getName());
		containerResource.setUnit(container.getUnit());
		containerResource.setTareWeight(container.getTareWeight());
		containerResource.setThresholdMax(container.getThresholdMax());
		containerResource.setThresholdMin(container.getThresholdMin());
		return containerResource;
	}

	public static StorageUnitResource from(StorageUnit storageUnit) {
		StorageUnitResource storageUnitResource = new StorageUnitResource();
		storageUnitResource.setId(storageUnit.getId());
		storageUnitResource.setName(storageUnit.getName());
		storageUnitResource.setEnabled(storageUnit.isEnabled());
		storageUnitResource.setContainers(from(storageUnit.getContainers()));
		return storageUnitResource;
	}
	
	private static List<List<ContainerResource>> from(List<Container> containers) {
		return new TreeMap<Long, List<Container>>(
				containers.stream().collect(groupingBy(Container::getLevel)))
						.values()
						.stream()
						.peek(l -> l.sort(Comparator.comparing(Container::getPosition)))
						.map(l -> l.stream().map(ResourceMapper::from).collect(Collectors.toList()))
						.collect(Collectors.toList());
	}

	public static SubscriberResource from(MqttSubscriber mqttSubscriber) {
		SubscriberResource subscriberResource = new SubscriberResource();
		subscriberResource.setId(mqttSubscriber.getClientId());
		subscriberResource.setTopic(mqttSubscriber.getTopic());
		subscriberResource.setActive(mqttSubscriber.isConnected());
		return subscriberResource;
	}
	
	public static ContainerSnapshotResource from(Container container, BigDecimal value) {
		ContainerSnapshotResource containerSnapResource = new ContainerSnapshotResource();
		containerSnapResource.setName(container.getName());
		containerSnapResource.setGrossValue(value);
		containerSnapResource.setUnit(container.getUnit());
		containerSnapResource.setTareWeight(container.getTareWeight());
		containerSnapResource.setThresholdMax(container.getThresholdMax());
		containerSnapResource.setThresholdMin(container.getThresholdMin());
		return containerSnapResource;
	}

	public static StorageUnitSnapshotResource from(StorageUnit storageUnit, Map<Container.ID, BigDecimal> containerValues) {
		StorageUnitSnapshotResource storageUnitSnapResource = new StorageUnitSnapshotResource();
		storageUnitSnapResource.setId(storageUnit.getId());
		storageUnitSnapResource.setName(storageUnit.getName());
		storageUnitSnapResource.setContainers(from(storageUnit.getContainers(), containerValues));
		return storageUnitSnapResource;
	}
	
	private static List<List<ContainerSnapshotResource>> from(List<Container> containers, Map<Container.ID, BigDecimal> containerValues) {
		return new TreeMap<Long, List<Container>>(
				containers.stream().collect(groupingBy(Container::getLevel)))
						.values()
						.stream()
						.peek(l -> l.sort(Comparator.comparing(Container::getPosition)))
						.map(l -> l.stream()
								.map(container -> ResourceMapper.from(container,
										containerValues.getOrDefault(container.getId(), BigDecimal.ZERO)))
								.collect(Collectors.toList()))
						.collect(Collectors.toList());
	}
}
