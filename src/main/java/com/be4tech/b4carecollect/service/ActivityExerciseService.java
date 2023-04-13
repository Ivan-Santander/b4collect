package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.ActivityExercise;
import com.be4tech.b4carecollect.repository.ActivityExerciseRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ActivityExercise}.
 */
@Service
@Transactional
public class ActivityExerciseService {

    private final Logger log = LoggerFactory.getLogger(ActivityExerciseService.class);

    private final ActivityExerciseRepository activityExerciseRepository;

    public ActivityExerciseService(ActivityExerciseRepository activityExerciseRepository) {
        this.activityExerciseRepository = activityExerciseRepository;
    }

    /**
     * Save a activityExercise.
     *
     * @param activityExercise the entity to save.
     * @return the persisted entity.
     */
    public ActivityExercise save(ActivityExercise activityExercise) {
        log.debug("Request to save ActivityExercise : {}", activityExercise);
        return activityExerciseRepository.save(activityExercise);
    }

    /**
     * Update a activityExercise.
     *
     * @param activityExercise the entity to save.
     * @return the persisted entity.
     */
    public ActivityExercise update(ActivityExercise activityExercise) {
        log.debug("Request to update ActivityExercise : {}", activityExercise);
        return activityExerciseRepository.save(activityExercise);
    }

    /**
     * Partially update a activityExercise.
     *
     * @param activityExercise the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ActivityExercise> partialUpdate(ActivityExercise activityExercise) {
        log.debug("Request to partially update ActivityExercise : {}", activityExercise);

        return activityExerciseRepository
            .findById(activityExercise.getId())
            .map(existingActivityExercise -> {
                if (activityExercise.getUsuarioId() != null) {
                    existingActivityExercise.setUsuarioId(activityExercise.getUsuarioId());
                }
                if (activityExercise.getEmpresaId() != null) {
                    existingActivityExercise.setEmpresaId(activityExercise.getEmpresaId());
                }
                if (activityExercise.getExercise() != null) {
                    existingActivityExercise.setExercise(activityExercise.getExercise());
                }
                if (activityExercise.getRepetitions() != null) {
                    existingActivityExercise.setRepetitions(activityExercise.getRepetitions());
                }
                if (activityExercise.getTypeResistence() != null) {
                    existingActivityExercise.setTypeResistence(activityExercise.getTypeResistence());
                }
                if (activityExercise.getResistenceKg() != null) {
                    existingActivityExercise.setResistenceKg(activityExercise.getResistenceKg());
                }
                if (activityExercise.getDuration() != null) {
                    existingActivityExercise.setDuration(activityExercise.getDuration());
                }
                if (activityExercise.getStartTime() != null) {
                    existingActivityExercise.setStartTime(activityExercise.getStartTime());
                }
                if (activityExercise.getEndTime() != null) {
                    existingActivityExercise.setEndTime(activityExercise.getEndTime());
                }

                return existingActivityExercise;
            })
            .map(activityExerciseRepository::save);
    }

    /**
     * Get all the activityExercises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ActivityExercise> findAll(Pageable pageable) {
        log.debug("Request to get all ActivityExercises");
        return activityExerciseRepository.findAll(pageable);
    }

    /**
     * Get one activityExercise by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ActivityExercise> findOne(UUID id) {
        log.debug("Request to get ActivityExercise : {}", id);
        return activityExerciseRepository.findById(id);
    }

    /**
     * Delete the activityExercise by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete ActivityExercise : {}", id);
        activityExerciseRepository.deleteById(id);
    }
}
