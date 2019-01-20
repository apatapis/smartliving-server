package di.smartliving.server.util;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import di.smartliving.server.domain.Container;
import di.smartliving.server.domain.MeasurementUnit;
import di.smartliving.server.dto.SensorMessage;
import di.smartliving.server.util.exception.CreateSensorMessageException;

public class DtoMapper {

	private DtoMapper() {
	};

	public static SensorMessage from(String topic, String sensorMessagePayloadAsString, Instant timestamp) {
		Container.ID containerId;
		SensorMessagePayload sensorMessagePayload;
		try {
			containerId = extractFrom(topic);
			sensorMessagePayload = deserialize(sensorMessagePayloadAsString);
		} catch (Exception e) {
			throw new CreateSensorMessageException();
		}
		SensorMessage sensorMessage = new SensorMessage();
		sensorMessage.setContainerId(containerId);
		sensorMessage.setUnit(sensorMessagePayload.getUnit());
		sensorMessage.setValue(sensorMessagePayload.getValue());
		return sensorMessage;
	}

	private static Container.ID extractFrom(String mqttTopic) throws Exception {
		String[] tokens = mqttTopic.split("/");
		Long storageUnitId = Long.parseLong(tokens[1]);
		Long containerLevel = Long.parseLong(tokens[3]);
		Long containerPosition = Long.parseLong(tokens[5]);
		return new Container.ID(storageUnitId, containerLevel, containerPosition);
	}

	private static SensorMessagePayload deserialize(String mqttMessagePayload) throws Exception {
		return new ObjectMapper().readValue(new String(mqttMessagePayload), SensorMessagePayload.class);
	}

	@SuppressWarnings("unused")
	private class SensorMessagePayload {

		private UUID sensorId;
		private MeasurementUnit unit;
		private BigDecimal value;

		public UUID getSensorId() {
			return sensorId;
		}

		public void setSensorId(UUID sensorId) {
			this.sensorId = sensorId;
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

	}

}
