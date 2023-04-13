package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.BloodGlucoseSummary;
import com.be4tech.b4carecollect.repository.BloodGlucoseSummaryRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BloodGlucoseSummary}.
 */
@Service
@Transactional
public class BloodGlucoseSummaryService {

    private final Logger log = LoggerFactory.getLogger(BloodGlucoseSummaryService.class);

    private final BloodGlucoseSummaryRepository bloodGlucoseSummaryRepository;

    public BloodGlucoseSummaryService(BloodGlucoseSummaryRepository bloodGlucoseSummaryRepository) {
        this.bloodGlucoseSummaryRepository = bloodGlucoseSummaryRepository;
    }

    /**
     * Save a bloodGlucoseSummary.
     *
     * @param bloodGlucoseSummary the entity to save.
     * @return the persisted entity.
     */
    public BloodGlucoseSummary save(BloodGlucoseSummary bloodGlucoseSummary) {
        log.debug("Request to save BloodGlucoseSummary : {}", bloodGlucoseSummary);
        return bloodGlucoseSummaryRepository.save(bloodGlucoseSummary);
    }

    /**
     * Update a bloodGlucoseSummary.
     *
     * @param bloodGlucoseSummary the entity to save.
     * @return the persisted entity.
     */
    public BloodGlucoseSummary update(BloodGlucoseSummary bloodGlucoseSummary) {
        log.debug("Request to update BloodGlucoseSummary : {}", bloodGlucoseSummary);
        return bloodGlucoseSummaryRepository.save(bloodGlucoseSummary);
    }

    /**
     * Partially update a bloodGlucoseSummary.
     *
     * @param bloodGlucoseSummary the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BloodGlucoseSummary> partialUpdate(BloodGlucoseSummary bloodGlucoseSummary) {
        log.debug("Request to partially update BloodGlucoseSummary : {}", bloodGlucoseSummary);

        return bloodGlucoseSummaryRepository
            .findById(bloodGlucoseSummary.getId())
            .map(existingBloodGlucoseSummary -> {
                if (bloodGlucoseSummary.getUsuarioId() != null) {
                    existingBloodGlucoseSummary.setUsuarioId(bloodGlucoseSummary.getUsuarioId());
                }
                if (bloodGlucoseSummary.getEmpresaId() != null) {
                    existingBloodGlucoseSummary.setEmpresaId(bloodGlucoseSummary.getEmpresaId());
                }
                if (bloodGlucoseSummary.getFieldAverage() != null) {
                    existingBloodGlucoseSummary.setFieldAverage(bloodGlucoseSummary.getFieldAverage());
                }
                if (bloodGlucoseSummary.getFieldMax() != null) {
                    existingBloodGlucoseSummary.setFieldMax(bloodGlucoseSummary.getFieldMax());
                }
                if (bloodGlucoseSummary.getFieldMin() != null) {
                    existingBloodGlucoseSummary.setFieldMin(bloodGlucoseSummary.getFieldMin());
                }
                if (bloodGlucoseSummary.getIntervalFood() != null) {
                    existingBloodGlucoseSummary.setIntervalFood(bloodGlucoseSummary.getIntervalFood());
                }
                if (bloodGlucoseSummary.getMealType() != null) {
                    existingBloodGlucoseSummary.setMealType(bloodGlucoseSummary.getMealType());
                }
                if (bloodGlucoseSummary.getRelationTemporalSleep() != null) {
                    existingBloodGlucoseSummary.setRelationTemporalSleep(bloodGlucoseSummary.getRelationTemporalSleep());
                }
                if (bloodGlucoseSummary.getSampleSource() != null) {
                    existingBloodGlucoseSummary.setSampleSource(bloodGlucoseSummary.getSampleSource());
                }
                if (bloodGlucoseSummary.getStartTime() != null) {
                    existingBloodGlucoseSummary.setStartTime(bloodGlucoseSummary.getStartTime());
                }
                if (bloodGlucoseSummary.getEndTime() != null) {
                    existingBloodGlucoseSummary.setEndTime(bloodGlucoseSummary.getEndTime());
                }

                return existingBloodGlucoseSummary;
            })
            .map(bloodGlucoseSummaryRepository::save);
    }

    /**
     * Get all the bloodGlucoseSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BloodGlucoseSummary> findAll(Pageable pageable) {
        log.debug("Request to get all BloodGlucoseSummaries");
        return bloodGlucoseSummaryRepository.findAll(pageable);
    }

    /**
     * Get one bloodGlucoseSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BloodGlucoseSummary> findOne(UUID id) {
        log.debug("Request to get BloodGlucoseSummary : {}", id);
        return bloodGlucoseSummaryRepository.findById(id);
    }

    /**
     * Delete the bloodGlucoseSummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete BloodGlucoseSummary : {}", id);
        bloodGlucoseSummaryRepository.deleteById(id);
    }
}
