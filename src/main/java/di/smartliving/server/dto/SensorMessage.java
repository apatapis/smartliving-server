package di.smartliving.server.dto;

import java.math.BigDecimal;
import java.util.Date;

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
	private Date timestamp;

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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
