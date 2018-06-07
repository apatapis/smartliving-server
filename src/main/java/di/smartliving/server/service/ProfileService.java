package di.smartliving.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import di.smartliving.server.domain.Profile;
import di.smartliving.server.domain.repository.ProfileRepository;
import di.smartliving.server.dto.ProfileDTO;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	public ProfileDTO getById(Long id) {
		Profile profile = profileRepository.findOne(id);
		return ProfileDTO.from(profile);
	}

	public void add(ProfileDTO profileDTO) {
		Profile profile = new Profile();
		profile.setName(profileDTO.getName());
		try {
			profile.setConfiguration(new ObjectMapper().writeValueAsString(profileDTO.getShelves()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		profileRepository.save(profile);
	}
}
