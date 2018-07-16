package di.smartliving.server.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import di.smartliving.server.domain.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	@Query("SELECT COUNT(p) > 0 from Profile p WHERE p.name = :name")
	public boolean existsWithName(@Param("name") String name);
}