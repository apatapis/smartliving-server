package di.smartliving.server.dto;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import di.smartliving.server.domain.Profile;

public class ProfileDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private List<List<Space>> shelves;

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

	@Override
	public String toString() {
		return "ProfileDTO [name=" + name + ", shelves=" + shelves + "]";
	}

	public static ProfileDTO from(Profile profile) {
		ProfileDTO profileDTO = new ProfileDTO();
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
