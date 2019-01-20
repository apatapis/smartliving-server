package di.smartliving.server.web.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import di.smartliving.server.service.StorageUnitService;
import di.smartliving.server.util.EntityMapper;
import di.smartliving.server.util.ResourceMapper;
import di.smartliving.server.web.rest.resource.StorageUnitResource;

@RestController
@RequestMapping(path = "api/storage-unit")
public class StorageUnitController {

	@Autowired
	private StorageUnitService storageUnitService;

	@PostMapping
	public void addStorageUnit(@RequestBody StorageUnitResource storageUnitResource) {
		storageUnitService.addStorageUnit(EntityMapper.from(storageUnitResource));
	}

	@GetMapping
	public List<StorageUnitResource> getStorageUnits(
			@RequestParam(required = false, name = "enabled") Boolean enabled) {
		return storageUnitService.getStorageUnits(enabled, new Sort(Direction.ASC, "id"))
								 .stream()
								 .map(ResourceMapper::from)
								 .collect(Collectors.toList());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<StorageUnitResource> getStorageUnit(@PathVariable Long id) {
		return storageUnitService.getStorageUnit(id)
								 .map(ResourceMapper::from)
								 .map(ResponseEntity::ok)
								 .orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PutMapping(path = "/{id}/name")
	public ResponseEntity<StorageUnitResource> updateStorageUnit(@PathVariable Long id, @RequestBody StorageUnitResource storageUnitResource) {
		return storageUnitService.getStorageUnit(id)
								 .map(ResourceMapper::from)
								 .map(ResponseEntity::ok)
								 .orElseGet(() -> ResponseEntity.notFound().build());
	}


}