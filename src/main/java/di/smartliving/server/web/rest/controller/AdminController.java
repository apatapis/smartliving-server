package di.smartliving.server.web.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import di.smartliving.server.dto.ProfileDTO;
import di.smartliving.server.service.ProfileService;

@RestController
@RequestMapping(path = "api/admin")
public class AdminController {

	@Autowired
	private ProfileService profileService;

	@PostMapping(path = "/profile")
	@ResponseStatus(HttpStatus.OK)
	public void addProfile(@RequestBody ProfileDTO profileDTO) {
		System.out.println(profileDTO);
		profileService.add(profileDTO);
	}
	
	@GetMapping(path = "/profile/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ProfileDTO getProfile(@PathVariable Long id) {
		return profileService.getById(id);
	}

}