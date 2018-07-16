package di.smartliving.server.web.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import di.smartliving.server.dto.ProfileDTO;
import di.smartliving.server.service.ProfileService;
import di.smartliving.server.web.rest.resource.UpdateProfileRequest;

@RestController
@RequestMapping(path = "api/admin")
public class AdminController {

	@Autowired
	private ProfileService profileService;

	@PostMapping(path = "/profile")
	public ResponseEntity<Void> addProfile(@RequestBody ProfileDTO profileDTO) {
		System.out.println(profileDTO);
		try {
			profileService.add(profileDTO);
		} catch (EntityExistsException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(path = "/profile")
	@ResponseStatus(HttpStatus.OK)
	public List<ProfileDTO> getProfiles() {
		return profileService.getAll();
	}

	@GetMapping(path = "/profile/{id}")
	public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long id) {
		Optional<ProfileDTO> profileDTO = profileService.getById(id);
		if (profileDTO.isPresent()) {
			return new ResponseEntity<>(profileDTO.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping(path = "/profile/{id}/load")
	@ResponseStatus(HttpStatus.OK)
	public void loadProfile(@PathVariable Long id) {
		profileService.loadProfile(id);
	}

	@PutMapping(path = "/profile/{id}")
	public ResponseEntity<Void> updateProfile(@PathVariable Long id,
			@RequestBody UpdateProfileRequest updateProfileRequest) {
		try {
			profileService.updateProfile(id, updateProfileRequest);
		} catch (EntityExistsException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(path = "/profile/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteProfile(@PathVariable Long id) {
		profileService.deleteProfile(id);
	}

}