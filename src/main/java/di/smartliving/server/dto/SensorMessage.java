package di.smartliving.server.dto;

import java.math.BigDecimal;
import java.time.Instant;

import di.smartliving.server.domain.Container;
import di.smartliving.server.domain.MeasurementUnit;

/**
 * Message sent out by a sensor via MQTT, decoded into meaningful values for the
 * server's application context.
 */
public class SensorMessage {

	private Container.ID containerId;
	private MeasurementUnit unit;
	private BigDecimal value;
	private Instant timestamp;

	public Container.ID getContainerId() {
		return containerId;
	}

	public void setContainerId(Container.ID containerId) {
		this.containerId = containerId;
	}

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
