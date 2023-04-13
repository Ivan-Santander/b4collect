package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.LocationSample;
import com.be4tech.b4carecollect.repository.LocationSampleRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LocationSample}.
 */
@Service
@Transactional
public class LocationSampleService {

    private final Logger log = LoggerFactory.getLogger(LocationSampleService.class);

    private final LocationSampleRepository locationSampleRepository;

    public LocationSampleService(LocationSampleRepository locationSampleRepository) {
        this.locationSampleRepository = locationSampleRepository;
    }

    /**
     * Save a locationSample.
     *
     * @param locationSample the entity to save.
     * @return the persisted entity.
     */
    public LocationSample save(LocationSample locationSample) {
        log.debug("Request to save LocationSample : {}", locationSample);
        return locationSampleRepository.save(locationSample);
    }

    /**
     * Update a locationSample.
     *
     * @param locationSample the entity to save.
     * @return the persisted entity.
     */
    public LocationSample update(LocationSample locationSample) {
        log.debug("Request to update LocationSample : {}", locationSample);
        return locationSampleRepository.save(locationSample);
    }

    /**
     * Partially update a locationSample.
     *
     * @param locationSample the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LocationSample> partialUpdate(LocationSample locationSample) {
        log.debug("Request to partially update LocationSample : {}", locationSample);

        return locationSampleRepository
            .findById(locationSample.getId())
            .map(existingLocationSample -> {
                if (locationSample.getUsuarioId() != null) {
                    existingLocationSample.setUsuarioId(locationSample.getUsuarioId());
                }
                if (locationSample.getEmpresaId() != null) {
                    existingLocationSample.setEmpresaId(locationSample.getEmpresaId());
                }
                if (locationSample.getLatitudMin() != null) {
                    existingLocationSample.setLatitudMin(locationSample.getLatitudMin());
                }
                if (locationSample.getLongitudMin() != null) {
                    existingLocationSample.setLongitudMin(locationSample.getLongitudMin());
                }
                if (locationSample.getLatitudMax() != null) {
                    existingLocationSample.setLatitudMax(locationSample.getLatitudMax());
                }
                if (locationSample.getLongitudMax() != null) {
                    existingLocationSample.setLongitudMax(locationSample.getLongitudMax());
                }
                if (locationSample.getAccuracy() != null) {
                    existingLocationSample.setAccuracy(locationSample.getAccuracy());
                }
                if (locationSample.getAltitud() != null) {
                    existingLocationSample.setAltitud(locationSample.getAltitud());
                }
                if (locationSample.getStartTime() != null) {
                    existingLocationSample.setStartTime(locationSample.getStartTime());
                }
                if (locationSample.getEndTime() != null) {
                    existingLocationSample.setEndTime(locationSample.getEndTime());
                }

                return existingLocationSample;
            })
            .map(locationSampleRepository::save);
    }

    /**
     * Get all the locationSamples.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocationSample> findAll(Pageable pageable) {
        log.debug("Request to get all LocationSamples");
        return locationSampleRepository.findAll(pageable);
    }

    /**
     * Get one locationSample by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocationSample> findOne(UUID id) {
        log.debug("Request to get LocationSample : {}", id);
        return locationSampleRepository.findById(id);
    }

    /**
     * Delete the locationSample by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete LocationSample : {}", id);
        locationSampleRepository.deleteById(id);
    }
}
