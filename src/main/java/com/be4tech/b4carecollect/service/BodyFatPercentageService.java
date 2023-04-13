package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.BodyFatPercentage;
import com.be4tech.b4carecollect.repository.BodyFatPercentageRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BodyFatPercentage}.
 */
@Service
@Transactional
public class BodyFatPercentageService {

    private final Logger log = LoggerFactory.getLogger(BodyFatPercentageService.class);

    private final BodyFatPercentageRepository bodyFatPercentageRepository;

    public BodyFatPercentageService(BodyFatPercentageRepository bodyFatPercentageRepository) {
        this.bodyFatPercentageRepository = bodyFatPercentageRepository;
    }

    /**
     * Save a bodyFatPercentage.
     *
     * @param bodyFatPercentage the entity to save.
     * @return the persisted entity.
     */
    public BodyFatPercentage save(BodyFatPercentage bodyFatPercentage) {
        log.debug("Request to save BodyFatPercentage : {}", bodyFatPercentage);
        return bodyFatPercentageRepository.save(bodyFatPercentage);
    }

    /**
     * Update a bodyFatPercentage.
     *
     * @param bodyFatPercentage the entity to save.
     * @return the persisted entity.
     */
    public BodyFatPercentage update(BodyFatPercentage bodyFatPercentage) {
        log.debug("Request to update BodyFatPercentage : {}", bodyFatPercentage);
        return bodyFatPercentageRepository.save(bodyFatPercentage);
    }

    /**
     * Partially update a bodyFatPercentage.
     *
     * @param bodyFatPercentage the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BodyFatPercentage> partialUpdate(BodyFatPercentage bodyFatPercentage) {
        log.debug("Request to partially update BodyFatPercentage : {}", bodyFatPercentage);

        return bodyFatPercentageRepository
            .findById(bodyFatPercentage.getId())
            .map(existingBodyFatPercentage -> {
                if (bodyFatPercentage.getUsuarioId() != null) {
                    existingBodyFatPercentage.setUsuarioId(bodyFatPercentage.getUsuarioId());
                }
                if (bodyFatPercentage.getEmpresaId() != null) {
                    existingBodyFatPercentage.setEmpresaId(bodyFatPercentage.getEmpresaId());
                }
                if (bodyFatPercentage.getFieldPorcentage() != null) {
                    existingBodyFatPercentage.setFieldPorcentage(bodyFatPercentage.getFieldPorcentage());
                }
                if (bodyFatPercentage.getEndTime() != null) {
                    existingBodyFatPercentage.setEndTime(bodyFatPercentage.getEndTime());
                }

                return existingBodyFatPercentage;
            })
            .map(bodyFatPercentageRepository::save);
    }

    /**
     * Get all the bodyFatPercentages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BodyFatPercentage> findAll(Pageable pageable) {
        log.debug("Request to get all BodyFatPercentages");
        return bodyFatPercentageRepository.findAll(pageable);
    }

    /**
     * Get one bodyFatPercentage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BodyFatPercentage> findOne(UUID id) {
        log.debug("Request to get BodyFatPercentage : {}", id);
        return bodyFatPercentageRepository.findById(id);
    }

    /**
     * Delete the bodyFatPercentage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete BodyFatPercentage : {}", id);
        bodyFatPercentageRepository.deleteById(id);
    }
}
