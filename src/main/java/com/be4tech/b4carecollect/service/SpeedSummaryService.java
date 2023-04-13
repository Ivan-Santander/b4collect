package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.SpeedSummary;
import com.be4tech.b4carecollect.repository.SpeedSummaryRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SpeedSummary}.
 */
@Service
@Transactional
public class SpeedSummaryService {

    private final Logger log = LoggerFactory.getLogger(SpeedSummaryService.class);

    private final SpeedSummaryRepository speedSummaryRepository;

    public SpeedSummaryService(SpeedSummaryRepository speedSummaryRepository) {
        this.speedSummaryRepository = speedSummaryRepository;
    }

    /**
     * Save a speedSummary.
     *
     * @param speedSummary the entity to save.
     * @return the persisted entity.
     */
    public SpeedSummary save(SpeedSummary speedSummary) {
        log.debug("Request to save SpeedSummary : {}", speedSummary);
        return speedSummaryRepository.save(speedSummary);
    }

    /**
     * Update a speedSummary.
     *
     * @param speedSummary the entity to save.
     * @return the persisted entity.
     */
    public SpeedSummary update(SpeedSummary speedSummary) {
        log.debug("Request to update SpeedSummary : {}", speedSummary);
        return speedSummaryRepository.save(speedSummary);
    }

    /**
     * Partially update a speedSummary.
     *
     * @param speedSummary the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SpeedSummary> partialUpdate(SpeedSummary speedSummary) {
        log.debug("Request to partially update SpeedSummary : {}", speedSummary);

        return speedSummaryRepository
            .findById(speedSummary.getId())
            .map(existingSpeedSummary -> {
                if (speedSummary.getUsuarioId() != null) {
                    existingSpeedSummary.setUsuarioId(speedSummary.getUsuarioId());
                }
                if (speedSummary.getEmpresaId() != null) {
                    existingSpeedSummary.setEmpresaId(speedSummary.getEmpresaId());
                }
                if (speedSummary.getFieldAverage() != null) {
                    existingSpeedSummary.setFieldAverage(speedSummary.getFieldAverage());
                }
                if (speedSummary.getFieldMax() != null) {
                    existingSpeedSummary.setFieldMax(speedSummary.getFieldMax());
                }
                if (speedSummary.getFieldMin() != null) {
                    existingSpeedSummary.setFieldMin(speedSummary.getFieldMin());
                }
                if (speedSummary.getStartTime() != null) {
                    existingSpeedSummary.setStartTime(speedSummary.getStartTime());
                }
                if (speedSummary.getEndTime() != null) {
                    existingSpeedSummary.setEndTime(speedSummary.getEndTime());
                }

                return existingSpeedSummary;
            })
            .map(speedSummaryRepository::save);
    }

    /**
     * Get all the speedSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SpeedSummary> findAll(Pageable pageable) {
        log.debug("Request to get all SpeedSummaries");
        return speedSummaryRepository.findAll(pageable);
    }

    /**
     * Get one speedSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SpeedSummary> findOne(UUID id) {
        log.debug("Request to get SpeedSummary : {}", id);
        return speedSummaryRepository.findById(id);
    }

    /**
     * Delete the speedSummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete SpeedSummary : {}", id);
        speedSummaryRepository.deleteById(id);
    }
}
