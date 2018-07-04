package di.smartliving.server.web.rest.resource;

import java.io.Serializable;

import di.smartliving.server.dto.Space;

public class SpaceSummary implements Serializable {

	private static final long serialVersionUID = 1L;

	private String product;

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "SpaceSummary [product=" + product + "]";
	}

	public static SpaceSummary from(Space space) {
		SpaceSummary spaceSummary = new SpaceSummary();
		spaceSummary.setProduct(space.getProduct());
		return spaceSummary;
	}
}
