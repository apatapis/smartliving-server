package di.smartliving.server.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import di.smartliving.server.domain.Container;
import di.smartliving.server.domain.Event;
import di.smartliving.server.domain.MeasurementUnit;
import di.smartliving.server.dto.ContainerValueChange;
import di.smartliving.server.dto.SensorMessage;
import di.smartliving.server.util.exception.CreateSensorMessageException;

public class DtoMapper {

	private DtoMapper() {
	};

	public static SensorMessage from(String topic, String sensorMessagePayloadAsString, Date timestamp) {
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
		sensorMessage.setTimestamp(timestamp);
		return sensorMessage;
	}

	private static Container.ID extractFrom(String mqttTopic) throws Exception {
		String[] tokens = mqttTopic.split("/");
		Long storageUnitId = Long.parseLong(tokens[0].split("_")[1]);
		Long containerLevel = Long.parseLong(tokens[1].split("_")[1]);
		Long containerPosition = Long.parseLong(tokens[2].split("_")[1]);
		return new Container.ID(storageUnitId, containerLevel, containerPosition);
	}

	private static SensorMessagePayload deserialize(String mqttMessagePayload) throws Exception {
		return new ObjectMapper().readValue(new String(mqttMessagePayload), SensorMessagePayload.class);
	}

	@SuppressWarnings("unused")
	private static class SensorMessagePayload {

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
	
	public static ContainerValueChange from(Event event) {
		ContainerValueChange containerValuechange = new ContainerValueChange();
		containerValuechange.setValue(event.getValue());
		containerValuechange.setTimestamp(event.getCreatedDate().toString());
		containerValuechange.setUnit(MeasurementUnit.KG);
		return containerValuechange;
	}

}
