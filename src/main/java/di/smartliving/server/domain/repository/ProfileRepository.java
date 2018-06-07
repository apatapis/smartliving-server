package di.smartliving.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import di.smartliving.server.domain.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}