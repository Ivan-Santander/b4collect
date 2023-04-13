package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.BloodGlucose;
import com.be4tech.b4carecollect.repository.BloodGlucoseRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BloodGlucose}.
 */
@Service
@Transactional
public class BloodGlucoseService {

    private final Logger log = LoggerFactory.getLogger(BloodGlucoseService.class);

    private final BloodGlucoseRepository bloodGlucoseRepository;

    public BloodGlucoseService(BloodGlucoseRepository bloodGlucoseRepository) {
        this.bloodGlucoseRepository = bloodGlucoseRepository;
    }

    /**
     * Save a bloodGlucose.
     *
     * @param bloodGlucose the entity to save.
     * @return the persisted entity.
     */
    public BloodGlucose save(BloodGlucose bloodGlucose) {
        log.debug("Request to save BloodGlucose : {}", bloodGlucose);
        return bloodGlucoseRepository.save(bloodGlucose);
    }

    /**
     * Update a bloodGlucose.
     *
     * @param bloodGlucose the entity to save.
     * @return the persisted entity.
     */
    public BloodGlucose update(BloodGlucose bloodGlucose) {
        log.debug("Request to update BloodGlucose : {}", bloodGlucose);
        return bloodGlucoseRepository.save(bloodGlucose);
    }

    /**
     * Partially update a bloodGlucose.
     *
     * @param bloodGlucose the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BloodGlucose> partialUpdate(BloodGlucose bloodGlucose) {
        log.debug("Request to partially update BloodGlucose : {}", bloodGlucose);

        return bloodGlucoseRepository
            .findById(bloodGlucose.getId())
            .map(existingBloodGlucose -> {
                if (bloodGlucose.getUsuarioId() != null) {
                    existingBloodGlucose.setUsuarioId(bloodGlucose.getUsuarioId());
                }
                if (bloodGlucose.getEmpresaId() != null) {
                    existingBloodGlucose.setEmpresaId(bloodGlucose.getEmpresaId());
                }
                if (bloodGlucose.getFieldBloodGlucoseLevel() != null) {
                    existingBloodGlucose.setFieldBloodGlucoseLevel(bloodGlucose.getFieldBloodGlucoseLevel());
                }
                if (bloodGlucose.getFieldTemporalRelationToMeal() != null) {
                    existingBloodGlucose.setFieldTemporalRelationToMeal(bloodGlucose.getFieldTemporalRelationToMeal());
                }
                if (bloodGlucose.getFieldMealType() != null) {
                    existingBloodGlucose.setFieldMealType(bloodGlucose.getFieldMealType());
                }
                if (bloodGlucose.getFieldTemporalRelationToSleep() != null) {
                    existingBloodGlucose.setFieldTemporalRelationToSleep(bloodGlucose.getFieldTemporalRelationToSleep());
                }
                if (bloodGlucose.getFieldBloodGlucoseSpecimenSource() != null) {
                    existingBloodGlucose.setFieldBloodGlucoseSpecimenSource(bloodGlucose.getFieldBloodGlucoseSpecimenSource());
                }
                if (bloodGlucose.getEndTime() != null) {
                    existingBloodGlucose.setEndTime(bloodGlucose.getEndTime());
                }

                return existingBloodGlucose;
            })
            .map(bloodGlucoseRepository::save);
    }

    /**
     * Get all the bloodGlucoses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BloodGlucose> findAll(Pageable pageable) {
        log.debug("Request to get all BloodGlucoses");
        return bloodGlucoseRepository.findAll(pageable);
    }

    /**
     * Get one bloodGlucose by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BloodGlucose> findOne(UUID id) {
        log.debug("Request to get BloodGlucose : {}", id);
        return bloodGlucoseRepository.findById(id);
    }

    /**
     * Delete the bloodGlucose by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete BloodGlucose : {}", id);
        bloodGlucoseRepository.deleteById(id);
    }
}
