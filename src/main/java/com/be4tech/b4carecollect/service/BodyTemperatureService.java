package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.BodyTemperature;
import com.be4tech.b4carecollect.repository.BodyTemperatureRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BodyTemperature}.
 */
@Service
@Transactional
public class BodyTemperatureService {

    private final Logger log = LoggerFactory.getLogger(BodyTemperatureService.class);

    private final BodyTemperatureRepository bodyTemperatureRepository;

    public BodyTemperatureService(BodyTemperatureRepository bodyTemperatureRepository) {
        this.bodyTemperatureRepository = bodyTemperatureRepository;
    }

    /**
     * Save a bodyTemperature.
     *
     * @param bodyTemperature the entity to save.
     * @return the persisted entity.
     */
    public BodyTemperature save(BodyTemperature bodyTemperature) {
        log.debug("Request to save BodyTemperature : {}", bodyTemperature);
        return bodyTemperatureRepository.save(bodyTemperature);
    }

    /**
     * Update a bodyTemperature.
     *
     * @param bodyTemperature the entity to save.
     * @return the persisted entity.
     */
    public BodyTemperature update(BodyTemperature bodyTemperature) {
        log.debug("Request to update BodyTemperature : {}", bodyTemperature);
        return bodyTemperatureRepository.save(bodyTemperature);
    }

    /**
     * Partially update a bodyTemperature.
     *
     * @param bodyTemperature the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BodyTemperature> partialUpdate(BodyTemperature bodyTemperature) {
        log.debug("Request to partially update BodyTemperature : {}", bodyTemperature);

        return bodyTemperatureRepository
            .findById(bodyTemperature.getId())
            .map(existingBodyTemperature -> {
                if (bodyTemperature.getUsuarioId() != null) {
                    existingBodyTemperature.setUsuarioId(bodyTemperature.getUsuarioId());
                }
                if (bodyTemperature.getEmpresaId() != null) {
                    existingBodyTemperature.setEmpresaId(bodyTemperature.getEmpresaId());
                }
                if (bodyTemperature.getFieldBodyTemperature() != null) {
                    existingBodyTemperature.setFieldBodyTemperature(bodyTemperature.getFieldBodyTemperature());
                }
                if (bodyTemperature.getFieldBodyTemperatureMeasureLocation() != null) {
                    existingBodyTemperature.setFieldBodyTemperatureMeasureLocation(
                        bodyTemperature.getFieldBodyTemperatureMeasureLocation()
                    );
                }
                if (bodyTemperature.getEndTime() != null) {
                    existingBodyTemperature.setEndTime(bodyTemperature.getEndTime());
                }

                return existingBodyTemperature;
            })
            .map(bodyTemperatureRepository::save);
    }

    /**
     * Get all the bodyTemperatures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BodyTemperature> findAll(Pageable pageable) {
        log.debug("Request to get all BodyTemperatures");
        return bodyTemperatureRepository.findAll(pageable);
    }

    /**
     * Get one bodyTemperature by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BodyTemperature> findOne(UUID id) {
        log.debug("Request to get BodyTemperature : {}", id);
        return bodyTemperatureRepository.findById(id);
    }

    /**
     * Delete the bodyTemperature by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete BodyTemperature : {}", id);
        bodyTemperatureRepository.deleteById(id);
    }
}
