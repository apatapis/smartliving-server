package di.smartliving.server.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "containers")
public class Container {

	@EmbeddedId
	private ID id;

	@MapsId("storageUnitId")
	@ManyToOne(fetch = FetchType.LAZY)
	private StorageUnit storageUnit;

	@Column(name = "name")
	private String name;

	@Column(name = "unit")
	@Enumerated(EnumType.STRING)
	private MeasurementUnit unit;

	@Column(name = "tare_weight")
	private BigDecimal tareWeight;

	@Column(name = "threshold_min")
	private BigDecimal thresholdMin;

	@Column(name = "threshold_max")
	private BigDecimal thresholdMax;

	@Column(name = "created_date", nullable = false, updatable = false)
	@CreatedDate
	private Date createdDate;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public StorageUnit getStorageUnit() {
		return storageUnit;
	}

	public void setStorageUnit(StorageUnit storageUnit) {
		this.storageUnit = storageUnit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Transient
	@JsonIgnore
	public Long getLevel() {
		return id.getLevel();
	}

	@Transient
	@JsonIgnore
	public Long getPosition() {
		return id.getPosition();
	}

	@Embeddable
	public static class ID implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "storage_unit_id")
		private Long storageUnitId;

		@Column(name = "level")
		private Long level;

		@Column(name = "position")
		private Long position;

		public ID() {
		}

		public ID(Long storageUnitId, Long level, Long position) {
			this.storageUnitId = storageUnitId;
			this.level = level;
			this.position = position;
		}

		public Long getStorageUnitId() {
			return storageUnitId;
		}

		public void setStorageUnitId(Long storageUnitId) {
			this.storageUnitId = storageUnitId;
		}

		public Long getLevel() {
			return level;
		}

		public void setLevel(Long level) {
			this.level = level;
		}

		public Long getPosition() {
			return position;
		}

		public void setPosition(Long position) {
			this.position = position;
		}

		@Override
		public String toString() {
			return "Container.ID [storageUnitId=" + storageUnitId + ", level=" + level + ", position=" + position + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((level == null) ? 0 : level.hashCode());
			result = prime * result + ((position == null) ? 0 : position.hashCode());
			result = prime * result + ((storageUnitId == null) ? 0 : storageUnitId.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ID other = (ID) obj;
			if (level == null) {
				if (other.level != null)
					return false;
			} else if (!level.equals(other.level))
				return false;
			if (position == null) {
				if (other.position != null)
					return false;
			} else if (!position.equals(other.position))
				return false;
			if (storageUnitId == null) {
				if (other.storageUnitId != null)
					return false;
			} else if (!storageUnitId.equals(other.storageUnitId))
				return false;
			return true;
		}

	}
}
