package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.StepCountCadence;
import com.be4tech.b4carecollect.repository.StepCountCadenceRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StepCountCadence}.
 */
@Service
@Transactional
public class StepCountCadenceService {

    private final Logger log = LoggerFactory.getLogger(StepCountCadenceService.class);

    private final StepCountCadenceRepository stepCountCadenceRepository;

    public StepCountCadenceService(StepCountCadenceRepository stepCountCadenceRepository) {
        this.stepCountCadenceRepository = stepCountCadenceRepository;
    }

    /**
     * Save a stepCountCadence.
     *
     * @param stepCountCadence the entity to save.
     * @return the persisted entity.
     */
    public StepCountCadence save(StepCountCadence stepCountCadence) {
        log.debug("Request to save StepCountCadence : {}", stepCountCadence);
        return stepCountCadenceRepository.save(stepCountCadence);
    }

    /**
     * Update a stepCountCadence.
     *
     * @param stepCountCadence the entity to save.
     * @return the persisted entity.
     */
    public StepCountCadence update(StepCountCadence stepCountCadence) {
        log.debug("Request to update StepCountCadence : {}", stepCountCadence);
        return stepCountCadenceRepository.save(stepCountCadence);
    }

    /**
     * Partially update a stepCountCadence.
     *
     * @param stepCountCadence the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StepCountCadence> partialUpdate(StepCountCadence stepCountCadence) {
        log.debug("Request to partially update StepCountCadence : {}", stepCountCadence);

        return stepCountCadenceRepository
            .findById(stepCountCadence.getId())
            .map(existingStepCountCadence -> {
                if (stepCountCadence.getUsuarioId() != null) {
                    existingStepCountCadence.setUsuarioId(stepCountCadence.getUsuarioId());
                }
                if (stepCountCadence.getEmpresaId() != null) {
                    existingStepCountCadence.setEmpresaId(stepCountCadence.getEmpresaId());
                }
                if (stepCountCadence.getRpm() != null) {
                    existingStepCountCadence.setRpm(stepCountCadence.getRpm());
                }
                if (stepCountCadence.getStartTime() != null) {
                    existingStepCountCadence.setStartTime(stepCountCadence.getStartTime());
                }
                if (stepCountCadence.getEndTime() != null) {
                    existingStepCountCadence.setEndTime(stepCountCadence.getEndTime());
                }

                return existingStepCountCadence;
            })
            .map(stepCountCadenceRepository::save);
    }

    /**
     * Get all the stepCountCadences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StepCountCadence> findAll(Pageable pageable) {
        log.debug("Request to get all StepCountCadences");
        return stepCountCadenceRepository.findAll(pageable);
    }

    /**
     * Get one stepCountCadence by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StepCountCadence> findOne(UUID id) {
        log.debug("Request to get StepCountCadence : {}", id);
        return stepCountCadenceRepository.findById(id);
    }

    /**
     * Delete the stepCountCadence by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete StepCountCadence : {}", id);
        stepCountCadenceRepository.deleteById(id);
    }
}
