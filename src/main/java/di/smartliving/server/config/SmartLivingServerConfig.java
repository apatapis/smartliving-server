package di.smartliving.server.config;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import di.smartliving.server.domain.StorageUnit;
import di.smartliving.server.domain.repository.StorageUnitRepository;
import di.smartliving.server.properties.MqttProperties;
import di.smartliving.server.properties.SmartLivingServerProperties;
import di.smartliving.server.web.mqtt.client.MqttSubscriber;

@Configuration
@EnableConfigurationProperties(value = { MqttProperties.class, SmartLivingServerProperties.class })
@EntityScan(basePackageClasses = StorageUnit.class)
@EnableJpaRepositories(basePackageClasses = StorageUnitRepository.class)
@EnableJpaAuditing
public class SmartLivingServerConfig {

	@Bean
	public ConcurrentHashMap<Long, Set<MqttSubscriber>> subscriberMap() {
		return new ConcurrentHashMap<>();
	}

}
