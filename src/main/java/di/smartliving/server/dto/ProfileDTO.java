package di.smartliving.server.dto;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import di.smartliving.server.domain.Profile;

public class ProfileDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private List<List<Space>> shelves;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<List<Space>> getShelves() {
		return shelves;
	}

	public void setShelves(List<List<Space>> shelves) {
		this.shelves = shelves;
	}

	@JsonIgnore
	public Space getSpaceByTopic(String topic) {
		return shelves.stream().flatMap(Collection::stream).filter(space -> topic.equals(space.getTopic())).findFirst()
				.get();
	}
	
	@Override
	public String toString() {
		return "ProfileDTO [id=" + id + ", name=" + name + ", shelves=" + shelves + "]";
	}

	public static ProfileDTO from(Profile profile) {
		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setId(profile.getId());
		profileDTO.setName(profile.getName());
		try {
			profileDTO.setShelves(
					new ObjectMapper().readValue(profile.getConfiguration(), new TypeReference<List<List<Space>>>() {
					}));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return profileDTO;
	}

}
