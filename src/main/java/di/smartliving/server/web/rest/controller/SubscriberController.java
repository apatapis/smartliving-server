package di.smartliving.server.web.rest.controller;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import di.smartliving.server.service.SubscriberService;
import di.smartliving.server.util.ResourceMapper;
import di.smartliving.server.web.rest.resource.SubscriberResource;

@RestController
@RequestMapping(path = "api/subscriber")
public class SubscriberController {

	@Autowired
	private SubscriberService subscriberService;

	@GetMapping
	public Map<Long, Set<SubscriberResource>> getSubscribers() {
		return subscriberService.getSubscriberMap()
								.entrySet()
								.stream()
								.collect(Collectors.toMap(entry -> entry.getKey(),
										entry -> entry.getValue().stream().map(ResourceMapper::from).collect(Collectors.toSet())));
	}
	
	@GetMapping(path = "/{storageUnitId}")
	public Set<SubscriberResource> getSubscribers(@PathVariable Long storageUnitId) {
		return subscriberService.getSubscribers(storageUnitId)
								.stream()
								.map(ResourceMapper::from)
								.collect(Collectors.toSet());
	}

}