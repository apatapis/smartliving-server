package di.smartliving.server.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "smart-living.server")
public class SmartLivingServerProperties {

	private Long maxStorageUnits;

	public Long getMaxStorageUnits() {
		return maxStorageUnits;
	}

	public void setMaxStorageUnits(Long maxStorageUnits) {
		this.maxStorageUnits = maxStorageUnits;
	}

}
