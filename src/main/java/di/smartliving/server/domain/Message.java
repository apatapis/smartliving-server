package di.smartliving.server.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import di.smartliving.server.dto.ProfileDTO;
import di.smartliving.server.web.mqtt.dto.SensorMessage;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "message")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "profile_id")
	private Long profileId;

	@Column(name = "topic")
	private String topic;

	@Column(name = "sensor_id")
	private String sensorId;

	@Column(name = "unit")
	private String unit;

	@Column(name = "value")
	private BigDecimal value;

	@Column(name = "product")
	private String product;

	@Column(name = "created_date", nullable = false, updatable = false)
	@CreatedDate
	private Instant createdDate;

	@Column(name = "modified_date")
	@LastModifiedDate
	private Instant modifiedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

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

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Instant getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Instant modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public static Message from(ProfileDTO profileDTO, String topic, SensorMessage sensorMessage) {
		Message message = new Message();
		message.setProfileId(profileDTO.getId());
		message.setTopic(topic);
		message.setProduct(profileDTO.getSpaceByTopic(topic).getProduct());
		message.setSensorId(sensorMessage.getSensorId());
		message.setUnit(sensorMessage.getUnit());
		message.setValue(sensorMessage.getValue());
		return message;
	}
}
