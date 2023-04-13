package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.StepCountDelta;
import com.be4tech.b4carecollect.repository.StepCountDeltaRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StepCountDelta}.
 */
@Service
@Transactional
public class StepCountDeltaService {

    private final Logger log = LoggerFactory.getLogger(StepCountDeltaService.class);

    private final StepCountDeltaRepository stepCountDeltaRepository;

    public StepCountDeltaService(StepCountDeltaRepository stepCountDeltaRepository) {
        this.stepCountDeltaRepository = stepCountDeltaRepository;
    }

    /**
     * Save a stepCountDelta.
     *
     * @param stepCountDelta the entity to save.
     * @return the persisted entity.
     */
    public StepCountDelta save(StepCountDelta stepCountDelta) {
        log.debug("Request to save StepCountDelta : {}", stepCountDelta);
        return stepCountDeltaRepository.save(stepCountDelta);
    }

    /**
     * Update a stepCountDelta.
     *
     * @param stepCountDelta the entity to save.
     * @return the persisted entity.
     */
    public StepCountDelta update(StepCountDelta stepCountDelta) {
        log.debug("Request to update StepCountDelta : {}", stepCountDelta);
        return stepCountDeltaRepository.save(stepCountDelta);
    }

    /**
     * Partially update a stepCountDelta.
     *
     * @param stepCountDelta the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StepCountDelta> partialUpdate(StepCountDelta stepCountDelta) {
        log.debug("Request to partially update StepCountDelta : {}", stepCountDelta);

        return stepCountDeltaRepository
            .findById(stepCountDelta.getId())
            .map(existingStepCountDelta -> {
                if (stepCountDelta.getUsuarioId() != null) {
                    existingStepCountDelta.setUsuarioId(stepCountDelta.getUsuarioId());
                }
                if (stepCountDelta.getEmpresaId() != null) {
                    existingStepCountDelta.setEmpresaId(stepCountDelta.getEmpresaId());
                }
                if (stepCountDelta.getSteps() != null) {
                    existingStepCountDelta.setSteps(stepCountDelta.getSteps());
                }
                if (stepCountDelta.getStartTime() != null) {
                    existingStepCountDelta.setStartTime(stepCountDelta.getStartTime());
                }
                if (stepCountDelta.getEndTime() != null) {
                    existingStepCountDelta.setEndTime(stepCountDelta.getEndTime());
                }

                return existingStepCountDelta;
            })
            .map(stepCountDeltaRepository::save);
    }

    /**
     * Get all the stepCountDeltas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StepCountDelta> findAll(Pageable pageable) {
        log.debug("Request to get all StepCountDeltas");
        return stepCountDeltaRepository.findAll(pageable);
    }

    /**
     * Get one stepCountDelta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StepCountDelta> findOne(UUID id) {
        log.debug("Request to get StepCountDelta : {}", id);
        return stepCountDeltaRepository.findById(id);
    }

    /**
     * Delete the stepCountDelta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete StepCountDelta : {}", id);
        stepCountDeltaRepository.deleteById(id);
    }
}
