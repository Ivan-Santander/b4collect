package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.PowerSummary;
import com.be4tech.b4carecollect.repository.PowerSummaryRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PowerSummary}.
 */
@Service
@Transactional
public class PowerSummaryService {

    private final Logger log = LoggerFactory.getLogger(PowerSummaryService.class);

    private final PowerSummaryRepository powerSummaryRepository;

    public PowerSummaryService(PowerSummaryRepository powerSummaryRepository) {
        this.powerSummaryRepository = powerSummaryRepository;
    }

    /**
     * Save a powerSummary.
     *
     * @param powerSummary the entity to save.
     * @return the persisted entity.
     */
    public PowerSummary save(PowerSummary powerSummary) {
        log.debug("Request to save PowerSummary : {}", powerSummary);
        return powerSummaryRepository.save(powerSummary);
    }

    /**
     * Update a powerSummary.
     *
     * @param powerSummary the entity to save.
     * @return the persisted entity.
     */
    public PowerSummary update(PowerSummary powerSummary) {
        log.debug("Request to update PowerSummary : {}", powerSummary);
        return powerSummaryRepository.save(powerSummary);
    }

    /**
     * Partially update a powerSummary.
     *
     * @param powerSummary the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PowerSummary> partialUpdate(PowerSummary powerSummary) {
        log.debug("Request to partially update PowerSummary : {}", powerSummary);

        return powerSummaryRepository
            .findById(powerSummary.getId())
            .map(existingPowerSummary -> {
                if (powerSummary.getUsuarioId() != null) {
                    existingPowerSummary.setUsuarioId(powerSummary.getUsuarioId());
                }
                if (powerSummary.getEmpresaId() != null) {
                    existingPowerSummary.setEmpresaId(powerSummary.getEmpresaId());
                }
                if (powerSummary.getFieldAverage() != null) {
                    existingPowerSummary.setFieldAverage(powerSummary.getFieldAverage());
                }
                if (powerSummary.getFieldMax() != null) {
                    existingPowerSummary.setFieldMax(powerSummary.getFieldMax());
                }
                if (powerSummary.getFieldMin() != null) {
                    existingPowerSummary.setFieldMin(powerSummary.getFieldMin());
                }
                if (powerSummary.getStartTime() != null) {
                    existingPowerSummary.setStartTime(powerSummary.getStartTime());
                }
                if (powerSummary.getEndTime() != null) {
                    existingPowerSummary.setEndTime(powerSummary.getEndTime());
                }

                return existingPowerSummary;
            })
            .map(powerSummaryRepository::save);
    }

    /**
     * Get all the powerSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PowerSummary> findAll(Pageable pageable) {
        log.debug("Request to get all PowerSummaries");
        return powerSummaryRepository.findAll(pageable);
    }

    /**
     * Get one powerSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PowerSummary> findOne(UUID id) {
        log.debug("Request to get PowerSummary : {}", id);
        return powerSummaryRepository.findById(id);
    }

    /**
     * Delete the powerSummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete PowerSummary : {}", id);
        powerSummaryRepository.deleteById(id);
    }
}
