package di.smartliving.server.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import di.smartliving.server.domain.Profile;
import di.smartliving.server.domain.repository.ProfileRepository;
import di.smartliving.server.global.StateManager;
import di.smartliving.server.properties.MqttProperties;

@Configuration
@EnableConfigurationProperties(value = MqttProperties.class)
@EntityScan(basePackageClasses = Profile.class)
@EnableJpaRepositories(basePackageClasses = ProfileRepository.class)
@EnableJpaAuditing
public class ServerAppConfig {

	@Bean
	@Scope("singleton")
	public StateManager stateManager() {
		return new StateManager();
	}
	
}
