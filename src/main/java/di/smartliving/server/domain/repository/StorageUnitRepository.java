package di.smartliving.server.domain.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import di.smartliving.server.domain.StorageUnit;

public interface StorageUnitRepository extends JpaRepository<StorageUnit, Long> {
	List<StorageUnit> findByEnabled(boolean enabled, Sort sort);
}