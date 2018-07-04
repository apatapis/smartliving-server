package di.smartliving.server.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import di.smartliving.server.web.rest.resource.UpdateSpaceDetailsRequest;

public class Space implements Serializable {

	private static final long serialVersionUID = 1L;

	private String topic;
	private String product;
	private BigDecimal tareWeight;
	private BigDecimal weightUpperBound;
	private BigDecimal weightLowerBound;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
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
		return "Space [topic=" + topic + ", product=" + product + ", tareWeight=" + tareWeight + ", weightUpperBound="
				+ weightUpperBound + ", weightLowerBound=" + weightLowerBound + "]";
	}

	public void copy(Space space) {
		setTopic(space.getTopic());
		setProduct(space.getProduct());
		setTareWeight(space.getTareWeight());
		setWeightLowerBound(space.getWeightLowerBound());
		setWeightUpperBound(space.getWeightUpperBound());
	}

	public static Space from(String topic, UpdateSpaceDetailsRequest updateSpaceDetailsRequest) {
		Space space = new Space();
		space.setTopic(topic);
		space.setProduct(updateSpaceDetailsRequest.getProduct());
		space.setTareWeight(updateSpaceDetailsRequest.getTareWeight());
		space.setWeightLowerBound(updateSpaceDetailsRequest.getWeightLowerBound());
		space.setWeightUpperBound(updateSpaceDetailsRequest.getWeightUpperBound());
		return space;
	}

}
