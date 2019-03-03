package di.smartliving.server.web.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import di.smartliving.server.service.StorageUnitCRUDService;
import di.smartliving.server.util.EntityMapper;
import di.smartliving.server.util.ResourceMapper;
import di.smartliving.server.web.rest.resource.ContainerResource;
import di.smartliving.server.web.rest.resource.StorageUnitResource;

@RestController
@RequestMapping(path = "api/storage-unit")
public class StorageUnitCRUDController {

	@Autowired
	private StorageUnitCRUDService storageUnitCRUDService;

	@PostMapping
	public StorageUnitResource addStorageUnit(@RequestBody StorageUnitResource storageUnitResource) {
		return ResourceMapper.from(storageUnitCRUDService.addStorageUnit(EntityMapper.from(storageUnitResource)));
	}

	@GetMapping
	public List<StorageUnitResource> getStorageUnits(
			@RequestParam(required = false, name = "enabled") Boolean enabled) {
		return storageUnitCRUDService.getStorageUnits(enabled, new Sort(Direction.ASC, "id"))
								 .stream()
								 .map(ResourceMapper::from)
								 .collect(Collectors.toList());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<StorageUnitResource> getStorageUnit(@PathVariable Long id) {
		return storageUnitCRUDService.getStorageUnit(id)
								 .map(ResourceMapper::from)
								 .map(ResponseEntity::ok)
								 .orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PutMapping(path = "/{id}/name")
	public StorageUnitResource updateStorageUnitName(@PathVariable Long id,
			@RequestBody StorageUnitResource storageUnitResource) {
		return ResourceMapper.from(storageUnitCRUDService.updateStorageUnitName(id, storageUnitResource.getName()));
	}
	
	@PutMapping(path = "/{id}/enabled")
	public StorageUnitResource updateStorageUnitEnabled(@PathVariable Long id,
			@RequestBody StorageUnitResource storageUnitResource) {
		return ResourceMapper.from(storageUnitCRUDService.updateStorageUnitEnabled(id, storageUnitResource.isEnabled()));
	}
	
	@DeleteMapping(path = "/{id}")
	public void deleteStorageUnit(@PathVariable Long id) {
		storageUnitCRUDService.deleteStorageUnit(id);
	}
	
	@PutMapping(path = "/{storageUnitId}/level/{level}/position/{position}")
	public ContainerResource updateContainer(@PathVariable Long storageUnitId, @PathVariable Long level,
			@PathVariable Long position, @RequestBody ContainerResource containerResource) {
		return ResourceMapper.from(storageUnitCRUDService
				.updateContainer(EntityMapper.from(containerResource, position, level, storageUnitCRUDService.getStorageUnit(storageUnitId).get())));
	}

}