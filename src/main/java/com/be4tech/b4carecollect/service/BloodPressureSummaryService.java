package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.BloodPressureSummary;
import com.be4tech.b4carecollect.repository.BloodPressureSummaryRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BloodPressureSummary}.
 */
@Service
@Transactional
public class BloodPressureSummaryService {

    private final Logger log = LoggerFactory.getLogger(BloodPressureSummaryService.class);

    private final BloodPressureSummaryRepository bloodPressureSummaryRepository;

    public BloodPressureSummaryService(BloodPressureSummaryRepository bloodPressureSummaryRepository) {
        this.bloodPressureSummaryRepository = bloodPressureSummaryRepository;
    }

    /**
     * Save a bloodPressureSummary.
     *
     * @param bloodPressureSummary the entity to save.
     * @return the persisted entity.
     */
    public BloodPressureSummary save(BloodPressureSummary bloodPressureSummary) {
        log.debug("Request to save BloodPressureSummary : {}", bloodPressureSummary);
        return bloodPressureSummaryRepository.save(bloodPressureSummary);
    }

    /**
     * Update a bloodPressureSummary.
     *
     * @param bloodPressureSummary the entity to save.
     * @return the persisted entity.
     */
    public BloodPressureSummary update(BloodPressureSummary bloodPressureSummary) {
        log.debug("Request to update BloodPressureSummary : {}", bloodPressureSummary);
        return bloodPressureSummaryRepository.save(bloodPressureSummary);
    }

    /**
     * Partially update a bloodPressureSummary.
     *
     * @param bloodPressureSummary the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BloodPressureSummary> partialUpdate(BloodPressureSummary bloodPressureSummary) {
        log.debug("Request to partially update BloodPressureSummary : {}", bloodPressureSummary);

        return bloodPressureSummaryRepository
            .findById(bloodPressureSummary.getId())
            .map(existingBloodPressureSummary -> {
                if (bloodPressureSummary.getUsuarioId() != null) {
                    existingBloodPressureSummary.setUsuarioId(bloodPressureSummary.getUsuarioId());
                }
                if (bloodPressureSummary.getEmpresaId() != null) {
                    existingBloodPressureSummary.setEmpresaId(bloodPressureSummary.getEmpresaId());
                }
                if (bloodPressureSummary.getFieldSistolicAverage() != null) {
                    existingBloodPressureSummary.setFieldSistolicAverage(bloodPressureSummary.getFieldSistolicAverage());
                }
                if (bloodPressureSummary.getFieldSistolicMax() != null) {
                    existingBloodPressureSummary.setFieldSistolicMax(bloodPressureSummary.getFieldSistolicMax());
                }
                if (bloodPressureSummary.getFieldSistolicMin() != null) {
                    existingBloodPressureSummary.setFieldSistolicMin(bloodPressureSummary.getFieldSistolicMin());
                }
                if (bloodPressureSummary.getFieldDiasatolicAverage() != null) {
                    existingBloodPressureSummary.setFieldDiasatolicAverage(bloodPressureSummary.getFieldDiasatolicAverage());
                }
                if (bloodPressureSummary.getFieldDiastolicMax() != null) {
                    existingBloodPressureSummary.setFieldDiastolicMax(bloodPressureSummary.getFieldDiastolicMax());
                }
                if (bloodPressureSummary.getFieldDiastolicMin() != null) {
                    existingBloodPressureSummary.setFieldDiastolicMin(bloodPressureSummary.getFieldDiastolicMin());
                }
                if (bloodPressureSummary.getBodyPosition() != null) {
                    existingBloodPressureSummary.setBodyPosition(bloodPressureSummary.getBodyPosition());
                }
                if (bloodPressureSummary.getMeasurementLocation() != null) {
                    existingBloodPressureSummary.setMeasurementLocation(bloodPressureSummary.getMeasurementLocation());
                }
                if (bloodPressureSummary.getStartTime() != null) {
                    existingBloodPressureSummary.setStartTime(bloodPressureSummary.getStartTime());
                }
                if (bloodPressureSummary.getEndTime() != null) {
                    existingBloodPressureSummary.setEndTime(bloodPressureSummary.getEndTime());
                }

                return existingBloodPressureSummary;
            })
            .map(bloodPressureSummaryRepository::save);
    }

    /**
     * Get all the bloodPressureSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BloodPressureSummary> findAll(Pageable pageable) {
        log.debug("Request to get all BloodPressureSummaries");
        return bloodPressureSummaryRepository.findAll(pageable);
    }

    /**
     * Get one bloodPressureSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BloodPressureSummary> findOne(UUID id) {
        log.debug("Request to get BloodPressureSummary : {}", id);
        return bloodPressureSummaryRepository.findById(id);
    }

    /**
     * Delete the bloodPressureSummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete BloodPressureSummary : {}", id);
        bloodPressureSummaryRepository.deleteById(id);
    }
}
