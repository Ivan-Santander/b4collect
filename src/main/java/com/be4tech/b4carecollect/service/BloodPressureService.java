package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.BloodPressure;
import com.be4tech.b4carecollect.repository.BloodPressureRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BloodPressure}.
 */
@Service
@Transactional
public class BloodPressureService {

    private final Logger log = LoggerFactory.getLogger(BloodPressureService.class);

    private final BloodPressureRepository bloodPressureRepository;

    public BloodPressureService(BloodPressureRepository bloodPressureRepository) {
        this.bloodPressureRepository = bloodPressureRepository;
    }

    /**
     * Save a bloodPressure.
     *
     * @param bloodPressure the entity to save.
     * @return the persisted entity.
     */
    public BloodPressure save(BloodPressure bloodPressure) {
        log.debug("Request to save BloodPressure : {}", bloodPressure);
        return bloodPressureRepository.save(bloodPressure);
    }

    /**
     * Update a bloodPressure.
     *
     * @param bloodPressure the entity to save.
     * @return the persisted entity.
     */
    public BloodPressure update(BloodPressure bloodPressure) {
        log.debug("Request to update BloodPressure : {}", bloodPressure);
        return bloodPressureRepository.save(bloodPressure);
    }

    /**
     * Partially update a bloodPressure.
     *
     * @param bloodPressure the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BloodPressure> partialUpdate(BloodPressure bloodPressure) {
        log.debug("Request to partially update BloodPressure : {}", bloodPressure);

        return bloodPressureRepository
            .findById(bloodPressure.getId())
            .map(existingBloodPressure -> {
                if (bloodPressure.getUsuarioId() != null) {
                    existingBloodPressure.setUsuarioId(bloodPressure.getUsuarioId());
                }
                if (bloodPressure.getEmpresaId() != null) {
                    existingBloodPressure.setEmpresaId(bloodPressure.getEmpresaId());
                }
                if (bloodPressure.getFieldBloodPressureSystolic() != null) {
                    existingBloodPressure.setFieldBloodPressureSystolic(bloodPressure.getFieldBloodPressureSystolic());
                }
                if (bloodPressure.getFieldBloodPressureDiastolic() != null) {
                    existingBloodPressure.setFieldBloodPressureDiastolic(bloodPressure.getFieldBloodPressureDiastolic());
                }
                if (bloodPressure.getFieldBodyPosition() != null) {
                    existingBloodPressure.setFieldBodyPosition(bloodPressure.getFieldBodyPosition());
                }
                if (bloodPressure.getFieldBloodPressureMeasureLocation() != null) {
                    existingBloodPressure.setFieldBloodPressureMeasureLocation(bloodPressure.getFieldBloodPressureMeasureLocation());
                }
                if (bloodPressure.getEndTime() != null) {
                    existingBloodPressure.setEndTime(bloodPressure.getEndTime());
                }

                return existingBloodPressure;
            })
            .map(bloodPressureRepository::save);
    }

    /**
     * Get all the bloodPressures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BloodPressure> findAll(Pageable pageable) {
        log.debug("Request to get all BloodPressures");
        return bloodPressureRepository.findAll(pageable);
    }

    /**
     * Get one bloodPressure by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BloodPressure> findOne(UUID id) {
        log.debug("Request to get BloodPressure : {}", id);
        return bloodPressureRepository.findById(id);
    }

    /**
     * Delete the bloodPressure by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete BloodPressure : {}", id);
        bloodPressureRepository.deleteById(id);
    }
}
