package di.smartliving.server.dto;

import java.math.BigDecimal;

import di.smartliving.server.domain.MeasurementUnit;

public class ContainerValueChange {

	private MeasurementUnit unit;
	private BigDecimal value;
	private String timestamp;

	public MeasurementUnit getUnit() {
		return unit;
	}

	public void setUnit(MeasurementUnit unit) {
		this.unit = unit;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
