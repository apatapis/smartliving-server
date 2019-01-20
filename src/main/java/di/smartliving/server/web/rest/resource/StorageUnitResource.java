package di.smartliving.server.web.rest.resource;

import java.util.List;

public class StorageUnitResource {

	private Long id;
	private String name;
	private boolean enabled;
	private List<List<ContainerResource>> containers;

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<List<ContainerResource>> getContainers() {
		return containers;
	}

	public void setContainers(List<List<ContainerResource>> containers) {
		this.containers = containers;
	}

}
