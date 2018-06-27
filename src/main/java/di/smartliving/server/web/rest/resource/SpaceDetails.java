package di.smartliving.server.web.rest.resource;

import java.io.Serializable;
import java.math.BigDecimal;

import di.smartliving.server.domain.Message;
import di.smartliving.server.dto.ProfileDTO;
import di.smartliving.server.dto.Space;

public class SpaceDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private String product;
	private String unit;
	private BigDecimal value;
	private BigDecimal tareWeight;
	private BigDecimal weightUpperBound;
	private BigDecimal weightLowerBound;

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

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
		return "SpaceDetails [product=" + product + ", unit=" + unit + ", value=" + value + ", tareWeight=" + tareWeight
				+ ", weightUpperBound=" + weightUpperBound + ", weightLowerBound=" + weightLowerBound + "]";
	}

	public static SpaceDetails from(ProfileDTO profileDTO, Message message) {
		SpaceDetails spaceDetails = new SpaceDetails();
		spaceDetails.setProduct(message.getProduct());
		spaceDetails.setUnit(message.getUnit());
		spaceDetails.setValue(message.getValue());
		Space space = profileDTO.getSpaceByTopic(message.getTopic());
		spaceDetails.setTareWeight(space.getTareWeight());
		spaceDetails.setWeightLowerBound(space.getWeightLowerBound());
		spaceDetails.setWeightUpperBound(space.getWeightUpperBound());
		return spaceDetails;
	}
}
