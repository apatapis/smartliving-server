package di.smartliving.server.web.rest.resource;

import java.math.BigDecimal;

import di.smartliving.server.domain.MeasurementUnit;

public class ContainerSnapshotResource {

	private String name;
	private BigDecimal grossValue;
	private MeasurementUnit unit;
	private BigDecimal tareWeight;
	private BigDecimal thresholdMin;
	private BigDecimal thresholdMax;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getGrossValue() {
		return grossValue;
	}

	public void setGrossValue(BigDecimal grossValue) {
		this.grossValue = grossValue;
	}

//	public BigDecimal getNetValue() {
//		return grossValue.subtract(tareWeight);
//	}
//	
	public MeasurementUnit getUnit() {
		return unit;
	}

	public void setUnit(MeasurementUnit unit) {
		this.unit = unit;
	}

	public BigDecimal getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(BigDecimal tareWeight) {
		this.tareWeight = tareWeight;
	}

	public BigDecimal getThresholdMin() {
		return thresholdMin;
	}

	public void setThresholdMin(BigDecimal thresholdMin) {
		this.thresholdMin = thresholdMin;
	}

	public BigDecimal getThresholdMax() {
		return thresholdMax;
	}

	public void setThresholdMax(BigDecimal thresholdMax) {
		this.thresholdMax = thresholdMax;
	}

}
