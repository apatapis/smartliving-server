package di.smartliving.server.web.rest.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import di.smartliving.server.domain.Container;
import di.smartliving.server.domain.StorageUnit;
import di.smartliving.server.service.StorageUnitSnapshotService;
import di.smartliving.server.util.ResourceMapper;
import di.smartliving.server.web.rest.resource.StorageUnitSnapshotResource;

@RestController
@RequestMapping(path = "api/storage-unit")
public class StorageUnitSnapshotController {

	@Autowired
	private StorageUnitSnapshotService storageUnitSnapshotService;

	@GetMapping(path = "/{id}/snapshot")
	public StorageUnitSnapshotResource getStorageUnitSnapshot(@PathVariable Long id) {
		Pair<StorageUnit, Map<Container.ID, BigDecimal>> pair = storageUnitSnapshotService.getSnapshot(id);
		return ResourceMapper.from(pair.getFirst(), pair.getSecond());
	}

}