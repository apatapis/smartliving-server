package di.smartliving.server.web.rest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import di.smartliving.server.dto.Space;
import di.smartliving.server.service.ClientService;
import di.smartliving.server.web.rest.resource.ProfileSnapshot;
import di.smartliving.server.web.rest.resource.SpaceDetails;
import di.smartliving.server.web.rest.resource.UpdateSpaceDetailsRequest;

@RestController
@RequestMapping(path = "api/client")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping(path = "/profile/active/snapshot")
	public ResponseEntity<ProfileSnapshot> getActiveProfileSnapshot() {
		Optional<ProfileSnapshot> profileSnapshot = clientService.getActiveProfileSnapshot();
		if (profileSnapshot.isPresent()) {
			return new ResponseEntity<>(profileSnapshot.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping(path = "/space/{profileId}/{topic}")
	public ResponseEntity<SpaceDetails> getLatestSpaceInformation(@PathVariable Long profileId,
			@PathVariable String topic) {
		topic = topic.replace('-', '/');
		Optional<SpaceDetails> spaceDetails = clientService.getLatestSpaceInformation(profileId, topic);
		if (spaceDetails.isPresent()) {
			return new ResponseEntity<>(spaceDetails.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping(path = "/space/{profileId}/{topic}")
	@ResponseStatus(HttpStatus.OK)
	public void updateSpaceDetails(@PathVariable Long profileId, @PathVariable String topic,
			@RequestBody UpdateSpaceDetailsRequest updateSpaceDetailsRequest) {
		topic = topic.replace('-', '/');
		clientService.updateSpaceDetails(profileId, Space.from(topic, updateSpaceDetailsRequest));
	}

}