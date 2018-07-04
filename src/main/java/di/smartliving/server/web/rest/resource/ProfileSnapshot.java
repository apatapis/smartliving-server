package di.smartliving.server.web.rest.resource;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import di.smartliving.server.dto.ProfileDTO;

public class ProfileSnapshot implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private List<List<SpaceSummary>> shelves;

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

	public List<List<SpaceSummary>> getShelves() {
		return shelves;
	}

	public void setShelves(List<List<SpaceSummary>> shelves) {
		this.shelves = shelves;
	}

	@Override
	public String toString() {
		return "ProfileSnapshot [id=" + id + ", name=" + name + ", shelves=" + shelves + "]";
	}

	public static ProfileSnapshot from(ProfileDTO profileDTO) {
		ProfileSnapshot profileSnapshot = new ProfileSnapshot();
		profileSnapshot.setName(profileDTO.getName());
		profileSnapshot.setShelves(profileDTO.getShelves().stream()
				.map(l -> l.stream().map(space -> SpaceSummary.from(space)).collect(Collectors.toList()))
				.collect(Collectors.toList()));
		return profileSnapshot;
	}
}
