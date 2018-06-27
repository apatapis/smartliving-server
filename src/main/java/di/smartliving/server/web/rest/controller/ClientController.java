package di.smartliving.server.web.rest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import di.smartliving.server.service.ClientService;
import di.smartliving.server.web.rest.resource.SpaceDetails;

@RestController
@RequestMapping(path = "api/client")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping(path = "/{profileId}/{topic}")
	public ResponseEntity<SpaceDetails> getLatestSpaceInformation(@PathVariable Long profileId,
			@PathVariable String topic) {
		Optional<SpaceDetails> spaceDetails = clientService.getLatestSpaceInformation(profileId,
				topic.replace('-', '/'));
		if (spaceDetails.isPresent()) {
			return new ResponseEntity<>(spaceDetails.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}