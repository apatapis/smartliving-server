package di.smartliving.server.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import di.smartliving.server.domain.Message;
import di.smartliving.server.domain.repository.MessageRepository;
import di.smartliving.server.dto.ProfileDTO;
import di.smartliving.server.dto.Space;
import di.smartliving.server.global.StateManager;
import di.smartliving.server.web.rest.resource.ProfileSnapshot;
import di.smartliving.server.web.rest.resource.SpaceDetails;

@Service
public class ClientService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private StateManager stateManager;

	public Optional<ProfileSnapshot> getActiveProfileSnapshot() {
		ProfileDTO profileDTO = stateManager.getActiveProfile();
		if (profileDTO == null) {
			return Optional.empty();
		}
		return Optional.of(ProfileSnapshot.from(profileDTO));
	}

	public Optional<SpaceDetails> getLatestSpaceInformation(Long profileId, String topic) {

		ProfileDTO profileDTO = stateManager.getActiveProfile().getId().equals(profileId)
				? stateManager.getActiveProfile()
				: profileService.getById(profileId).get();

		Optional<Message> message = messageRepository.findFirstByProfileIdAndTopicOrderByCreatedDateDesc(profileId,
				topic);

		return message.isPresent() ? Optional.of(SpaceDetails.from(profileDTO, message.get())) : Optional.empty();

	}

	public synchronized void updateSpaceDetails(Long profileId, Space space) {
		Optional<ProfileDTO> profileDTO = profileService.updateSpace(profileId, space);
		profileDTO.ifPresent(p -> {
			if (stateManager.isActive(p)) {
				stateManager.setActiveProfile(p);
			}
		});
	}
}
