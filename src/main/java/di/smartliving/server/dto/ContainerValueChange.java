package di.smartliving.server.dto;

import java.math.BigDecimal;
import java.time.Instant;

import di.smartliving.server.domain.MeasurementUnit;

public class ContainerValueChange {

	private MeasurementUnit unit;
	private BigDecimal value;
	private Instant timestamp;

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

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

}
