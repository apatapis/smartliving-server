package di.smartliving.server.domain;

import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Embedded
	@AttributeOverrides({ 
		@AttributeOverride(name = "level", column = @Column(name = "container_level")),
		@AttributeOverride(name = "position", column = @Column(name = "container_position"))
	})
	private Container.ID containerId;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private EventType type;

	@Column(name = "name")
	private String name;

	@Column(name = "value")
	private BigDecimal value;

	@Column(name = "created_date", nullable = false, updatable = false)
	private Instant createdDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Container.ID getContainerId() {
		return containerId;
	}

	public void setContainerId(Container.ID containerId) {
		this.containerId = containerId;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

}
