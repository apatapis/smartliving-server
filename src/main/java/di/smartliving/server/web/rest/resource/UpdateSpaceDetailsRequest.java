package di.smartliving.server.web.rest.resource;

import java.io.Serializable;
import java.math.BigDecimal;

public class UpdateSpaceDetailsRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String product;
	private BigDecimal tareWeight;
	private BigDecimal weightUpperBound;
	private BigDecimal weightLowerBound;

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public BigDecimal getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(BigDecimal tareWeight) {
		this.tareWeight = tareWeight;
	}

	public BigDecimal getWeightUpperBound() {
		return weightUpperBound;
	}

	public void setWeightUpperBound(BigDecimal weightUpperBound) {
		this.weightUpperBound = weightUpperBound;
	}

	public BigDecimal getWeightLowerBound() {
		return weightLowerBound;
	}

	public void setWeightLowerBound(BigDecimal weightLowerBound) {
		this.weightLowerBound = weightLowerBound;
	}

	@Override
	public String toString() {
		return "Space [product=" + product + ", tareWeight=" + tareWeight + ", weightUpperBound=" + weightUpperBound
				+ ", weightLowerBound=" + weightLowerBound + "]";
	}

}
