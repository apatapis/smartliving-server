package di.smartliving.server.web.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import di.smartliving.server.service.StorageUnitTrackingService;
import di.smartliving.server.web.rest.resource.StorageUnitTrackingResource;

@RestController
@RequestMapping(path = "api/storage-unit")
public class StorageUnitTrackingController {

	@Autowired
	private StorageUnitTrackingService storageUnitTrackingService;

	@PutMapping(path = "/{id}/tracking")
	public void updateStorageUnitName(@PathVariable Long id,
			@RequestBody StorageUnitTrackingResource storageUnitTrackingResource) {
		if (storageUnitTrackingResource.getTracking()) {
			storageUnitTrackingService.track(id, storageUnitTrackingResource.getSubscriptionStrategy());
		} else {
			storageUnitTrackingService.stopTracking(id);
		}
	}

}