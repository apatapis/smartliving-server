package di.smartliving.server.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import di.smartliving.server.domain.Profile;
import di.smartliving.server.domain.repository.ProfileRepository;
import di.smartliving.server.dto.ProfileDTO;
import di.smartliving.server.global.StateManager;
import di.smartliving.server.properties.MqttProperties;
import di.smartliving.server.web.mqtt.client.MqttSubscriberFactory;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private MqttSubscriberFactory mqttSubscriberFactory;
	
	@Autowired
	private StateManager stateManager;
	
	@Autowired
	private MqttProperties mqttProperties;

	public Optional<ProfileDTO> getById(Long id) {
		Profile profile = profileRepository.findOne(id);
		return profile == null ? Optional.empty() : Optional.of(ProfileDTO.from(profile));
	}

	public List<ProfileDTO> getAll() {
		return profileRepository.findAll(new Sort(Direction.ASC, "id")).stream()
				.map(profile -> ProfileDTO.from(profile)).collect(Collectors.toList());
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
	
	public synchronized void loadProfile(Long id) {
		Optional<ProfileDTO> profileDTO = getById(id);
		if (!profileDTO.isPresent()) {
			System.out.println("Profile not found. Aborting operation.");
		}
		stateManager.setActiveProfile(profileDTO.get());
		stateManager.clearSubscribers();
		stateManager
				.addSubscribers(profileDTO.get().getShelves()
						.stream()
						.flatMap(Collection::stream)
						.collect(Collectors.toMap(space -> space.getTopic(),
								space -> mqttSubscriberFactory.create(mqttProperties.getBrokerUrl())
				)));
		stateManager.startSubscribers();
	}
}
