package di.smartliving.server.util;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import di.smartliving.server.domain.Container;
import di.smartliving.server.domain.StorageUnit;
import di.smartliving.server.dto.SubscriptionStrategy;

public class SubscriptionHelper {

	private SubscriptionHelper() {
	};

	public static Set<String> getTopics(StorageUnit storageUnit, SubscriptionStrategy subscriptionStrategy) {
		switch (subscriptionStrategy) {
		case STORAGE_UNIT:
			return getStorageUnitTopics(storageUnit.getId());
		case LEVEL:
			return getLevelTopics(storageUnit.getId(), storageUnit.getContainers());
		case CONTAINER:
			return getContainerTopics(storageUnit.getId(), storageUnit.getContainers());
		default:
			return Collections.emptySet();
		}
	}

	private static Set<String> getStorageUnitTopics(Long storageUnitId) {
		return Collections.singleton(createTopic(storageUnitId, null, null));
	}

	private static Set<String> getLevelTopics(Long storageUnitId, List<Container> containers) {
		return containers.stream().collect(Collectors.groupingBy(Container::getLevel)).keySet().stream()
				.map(level -> createTopic(storageUnitId, level, null)).collect(Collectors.toSet());
	}

	private static Set<String> getContainerTopics(Long storageUnitId, List<Container> containers) {
		return containers.stream()
				.map(container -> createTopic(storageUnitId, container.getLevel(), container.getPosition()))
				.collect(Collectors.toSet());
	}

	private static String createTopic(Long storageUnitId, Long level, Long container) {
		String topic = "storage-unit_" + storageUnitId.toString();
		if (level == null) {
			return topic + "/#";
		}
		topic += "/level_" + level.toString();
		if (container == null) {
			return topic + "/#";
		}
		return topic += "/container_" + container.toString();
	}

}
