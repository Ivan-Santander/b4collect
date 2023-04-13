package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.ActivitySummary;
import com.be4tech.b4carecollect.repository.ActivitySummaryRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ActivitySummary}.
 */
@Service
@Transactional
public class ActivitySummaryService {

    private final Logger log = LoggerFactory.getLogger(ActivitySummaryService.class);

    private final ActivitySummaryRepository activitySummaryRepository;

    public ActivitySummaryService(ActivitySummaryRepository activitySummaryRepository) {
        this.activitySummaryRepository = activitySummaryRepository;
    }

    /**
     * Save a activitySummary.
     *
     * @param activitySummary the entity to save.
     * @return the persisted entity.
     */
    public ActivitySummary save(ActivitySummary activitySummary) {
        log.debug("Request to save ActivitySummary : {}", activitySummary);
        return activitySummaryRepository.save(activitySummary);
    }

    /**
     * Update a activitySummary.
     *
     * @param activitySummary the entity to save.
     * @return the persisted entity.
     */
    public ActivitySummary update(ActivitySummary activitySummary) {
        log.debug("Request to update ActivitySummary : {}", activitySummary);
        return activitySummaryRepository.save(activitySummary);
    }

    /**
     * Partially update a activitySummary.
     *
     * @param activitySummary the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ActivitySummary> partialUpdate(ActivitySummary activitySummary) {
        log.debug("Request to partially update ActivitySummary : {}", activitySummary);

        return activitySummaryRepository
            .findById(activitySummary.getId())
            .map(existingActivitySummary -> {
                if (activitySummary.getUsuarioId() != null) {
                    existingActivitySummary.setUsuarioId(activitySummary.getUsuarioId());
                }
                if (activitySummary.getEmpresaId() != null) {
                    existingActivitySummary.setEmpresaId(activitySummary.getEmpresaId());
                }
                if (activitySummary.getFieldActivity() != null) {
                    existingActivitySummary.setFieldActivity(activitySummary.getFieldActivity());
                }
                if (activitySummary.getFieldDuration() != null) {
                    existingActivitySummary.setFieldDuration(activitySummary.getFieldDuration());
                }
                if (activitySummary.getFieldNumSegments() != null) {
                    existingActivitySummary.setFieldNumSegments(activitySummary.getFieldNumSegments());
                }
                if (activitySummary.getStartTime() != null) {
                    existingActivitySummary.setStartTime(activitySummary.getStartTime());
                }
                if (activitySummary.getEndTime() != null) {
                    existingActivitySummary.setEndTime(activitySummary.getEndTime());
                }

                return existingActivitySummary;
            })
            .map(activitySummaryRepository::save);
    }

    /**
     * Get all the activitySummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ActivitySummary> findAll(Pageable pageable) {
        log.debug("Request to get all ActivitySummaries");
        return activitySummaryRepository.findAll(pageable);
    }

    /**
     * Get one activitySummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ActivitySummary> findOne(UUID id) {
        log.debug("Request to get ActivitySummary : {}", id);
        return activitySummaryRepository.findById(id);
    }

    /**
     * Delete the activitySummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete ActivitySummary : {}", id);
        activitySummaryRepository.deleteById(id);
    }
}
