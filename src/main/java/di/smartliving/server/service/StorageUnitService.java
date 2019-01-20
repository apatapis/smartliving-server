package di.smartliving.server.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import di.smartliving.server.domain.StorageUnit;
import di.smartliving.server.domain.repository.ContainerRepository;
import di.smartliving.server.domain.repository.StorageUnitRepository;

@Service
public class StorageUnitService {

	private static Logger LOGGER = LoggerFactory.getLogger(StorageUnitService.class);

	@Autowired
	private StorageUnitRepository storageUnitRepository;

	@Autowired
	private ContainerRepository containerRepository;

	public List<StorageUnit> getStorageUnits(Boolean enabled, Sort sort) {
		return enabled == null ? storageUnitRepository.findAll(sort)
				: storageUnitRepository.findByEnabled(enabled, sort);
	}

	public Optional<StorageUnit> getStorageUnit(Long id) {
		return Optional.ofNullable(storageUnitRepository.findOne(id));
	}

	public StorageUnit addStorageUnit(StorageUnit storageUnit) {
		if (storageUnitRepository.exists(storageUnit.getId())) {
			LOGGER.error("A storage unit with id {} already exists.", storageUnit.getId());
			throw new EntityExistsException("A storage unit with id " + storageUnit.getId() + " already exists.");
		}
		return storageUnitRepository.save(storageUnit);
	}

}
