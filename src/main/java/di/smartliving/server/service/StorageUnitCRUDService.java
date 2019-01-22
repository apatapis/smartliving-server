package di.smartliving.server.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import di.smartliving.server.domain.Container;
import di.smartliving.server.domain.StorageUnit;
import di.smartliving.server.domain.repository.ContainerRepository;
import di.smartliving.server.domain.repository.StorageUnitRepository;
import di.smartliving.server.properties.SmartLivingServerProperties;
import di.smartliving.server.web.rest.exception.RestException;

/**
 * Service responsible for storage unit CRUD operations.
 *
 */
@Service
public class StorageUnitCRUDService {

	private static Logger LOGGER = LoggerFactory.getLogger(StorageUnitCRUDService.class);

	@Autowired
	private SmartLivingServerProperties serverProperties;

	@Autowired
	private StorageUnitRepository storageUnitRepository;

	@Autowired
	private ContainerRepository containerRepository;

	/**
	 * Retrieve storage units ordered by id, optionally filtered by enabled.
	 * 
	 * @param enabled
	 * @param sort
	 * @return
	 */
	public List<StorageUnit> getStorageUnits(Boolean enabled, Sort sort) {
		return enabled == null ? storageUnitRepository.findAll(sort)
				: storageUnitRepository.findByEnabled(enabled, sort);
	}

	/**
	 * Find storage unit with the given id.
	 * 
	 * @param id
	 * @return
	 */
	public Optional<StorageUnit> getStorageUnit(Long id) {
		return Optional.ofNullable(storageUnitRepository.findOne(id));
	}

	/**
	 * Add a new created storage unit.
	 * 
	 * @param storageUnit
	 * @return
	 */
	public StorageUnit addStorageUnit(StorageUnit storageUnit) {
		if (storageUnitRepository.count() >= serverProperties.getMaxStorageUnits()) {
			throw RestException.create(HttpStatus.BAD_REQUEST,
					"Limit of " + serverProperties.getMaxStorageUnits() + " storage units has been reached.");
		}
		if (storageUnit.getId() != null && storageUnitRepository.exists(storageUnit.getId())) {
			LOGGER.error("A storage unit with id {} already exists.", storageUnit.getId());
			throw RestException.create(HttpStatus.BAD_REQUEST,
					"A storage unit with id " + storageUnit.getId() + " already exists.");
		}
		return storageUnitRepository.save(storageUnit);
	}

	/**
	 * Update a storage unit's name.
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public StorageUnit updateStorageUnitName(Long id, String name) {
		StorageUnit storageUnit = findStorageUnitOrThrowException(id);
		storageUnit.setName(name);
		return storageUnitRepository.save(storageUnit);
	}

	/**
	 * Enable or disable a storage unit.
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public StorageUnit updateStorageUnitEnabled(Long id, boolean enabled) {
		StorageUnit storageUnit = findStorageUnitOrThrowException(id);
		storageUnit.setEnabled(enabled);
		return storageUnitRepository.save(storageUnit);
	}

	/**
	 * Delete a storage unit.
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public void deleteStorageUnit(Long id) {
		StorageUnit storageUnit = findStorageUnitOrThrowException(id);
		storageUnitRepository.delete(storageUnit);
	}

	/**
	 * Update specified container.
	 * 
	 * @param storageUnit
	 * @param level
	 * @param position
	 * @return
	 */
	public Container updateContainer(Container updatedContainer) {
		Container existingContainer = containerRepository.findOne(updatedContainer.getId());
		if (existingContainer == null) {
			throw RestException.create(HttpStatus.NOT_FOUND, "Container " + updatedContainer.getId() + " not found.");
		}
		existingContainer.setName(updatedContainer.getName());
		existingContainer.setThresholdMax(updatedContainer.getThresholdMax());
		existingContainer.setThresholdMin(updatedContainer.getThresholdMin());
		return containerRepository.save(existingContainer);
	}
	
	private StorageUnit findStorageUnitOrThrowException(Long id) {
		StorageUnit storageUnit = storageUnitRepository.findOne(id);
		if (storageUnit == null) {
			throw RestException.create(HttpStatus.NOT_FOUND, "Storage unit with id " + id + " not found.");
		}
		return storageUnit;
	}

}
