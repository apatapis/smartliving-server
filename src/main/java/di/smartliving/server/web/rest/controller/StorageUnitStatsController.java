package di.smartliving.server.web.rest.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import di.smartliving.server.domain.Container;
import di.smartliving.server.dto.ContainerValueChange;
import di.smartliving.server.service.StorageUnitStatsService;

@RestController
@RequestMapping(path = "api/storage-unit")
public class StorageUnitStatsController {

	@Autowired
	private StorageUnitStatsService storageUnitStatsService;

	@GetMapping(path = "/{storageUnitId}/level/{level}/position/{position}/value-changes")
	public List<ContainerValueChange> getContainerValueChanges(@PathVariable Long storageUnitId,
			@PathVariable Long level, @PathVariable Long position, @RequestParam int page, @RequestParam int size) {
		return storageUnitStatsService.getContainerValueChanges(new Container.ID(storageUnitId, level, position),
				new PageRequest(page, size, new Sort(Direction.DESC, "createdDate"))).getContent();
	}

	@GetMapping(path = "/{storageUnitId}/level/{level}/position/{position}/zeros")
	public List<ContainerValueChange> getContainerZeros(@PathVariable Long storageUnitId, @PathVariable Long level,
			@PathVariable Long position) {
		return storageUnitStatsService.getContainerZeros(new Container.ID(storageUnitId, level, position));
	}

	@GetMapping(path = "/{storageUnitId}/level/{level}/position/{position}/average")
	public BigDecimal getContainerAverage(@PathVariable Long storageUnitId, @PathVariable Long level,
			@PathVariable Long position) {
		return storageUnitStatsService.getContainerAverage(new Container.ID(storageUnitId, level, position));
	}

}