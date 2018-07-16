package di.smartliving.server.web.rest.resource;

import java.io.Serializable;

public class UpdateProfileRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UpdateProfileRequest [name=" + name + "]";
	}

}
