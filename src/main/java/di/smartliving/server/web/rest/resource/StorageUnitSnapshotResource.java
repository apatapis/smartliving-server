package di.smartliving.server.web.rest.resource;

import java.util.List;

public class StorageUnitSnapshotResource {

	private Long id;
	private String name;
	private List<List<ContainerSnapshotResource>> containers;

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

	public List<List<ContainerSnapshotResource>> getContainers() {
		return containers;
	}

	public void setContainers(List<List<ContainerSnapshotResource>> containers) {
		this.containers = containers;
	}

}
