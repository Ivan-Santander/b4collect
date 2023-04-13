package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.HeartRateSummary;
import com.be4tech.b4carecollect.repository.HeartRateSummaryRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HeartRateSummary}.
 */
@Service
@Transactional
public class HeartRateSummaryService {

    private final Logger log = LoggerFactory.getLogger(HeartRateSummaryService.class);

    private final HeartRateSummaryRepository heartRateSummaryRepository;

    public HeartRateSummaryService(HeartRateSummaryRepository heartRateSummaryRepository) {
        this.heartRateSummaryRepository = heartRateSummaryRepository;
    }

    /**
     * Save a heartRateSummary.
     *
     * @param heartRateSummary the entity to save.
     * @return the persisted entity.
     */
    public HeartRateSummary save(HeartRateSummary heartRateSummary) {
        log.debug("Request to save HeartRateSummary : {}", heartRateSummary);
        return heartRateSummaryRepository.save(heartRateSummary);
    }

    /**
     * Update a heartRateSummary.
     *
     * @param heartRateSummary the entity to save.
     * @return the persisted entity.
     */
    public HeartRateSummary update(HeartRateSummary heartRateSummary) {
        log.debug("Request to update HeartRateSummary : {}", heartRateSummary);
        return heartRateSummaryRepository.save(heartRateSummary);
    }

    /**
     * Partially update a heartRateSummary.
     *
     * @param heartRateSummary the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HeartRateSummary> partialUpdate(HeartRateSummary heartRateSummary) {
        log.debug("Request to partially update HeartRateSummary : {}", heartRateSummary);

        return heartRateSummaryRepository
            .findById(heartRateSummary.getId())
            .map(existingHeartRateSummary -> {
                if (heartRateSummary.getUsuarioId() != null) {
                    existingHeartRateSummary.setUsuarioId(heartRateSummary.getUsuarioId());
                }
                if (heartRateSummary.getEmpresaId() != null) {
                    existingHeartRateSummary.setEmpresaId(heartRateSummary.getEmpresaId());
                }
                if (heartRateSummary.getFieldAverage() != null) {
                    existingHeartRateSummary.setFieldAverage(heartRateSummary.getFieldAverage());
                }
                if (heartRateSummary.getFieldMax() != null) {
                    existingHeartRateSummary.setFieldMax(heartRateSummary.getFieldMax());
                }
                if (heartRateSummary.getFieldMin() != null) {
                    existingHeartRateSummary.setFieldMin(heartRateSummary.getFieldMin());
                }
                if (heartRateSummary.getStartTime() != null) {
                    existingHeartRateSummary.setStartTime(heartRateSummary.getStartTime());
                }
                if (heartRateSummary.getEndTime() != null) {
                    existingHeartRateSummary.setEndTime(heartRateSummary.getEndTime());
                }

                return existingHeartRateSummary;
            })
            .map(heartRateSummaryRepository::save);
    }

    /**
     * Get all the heartRateSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HeartRateSummary> findAll(Pageable pageable) {
        log.debug("Request to get all HeartRateSummaries");
        return heartRateSummaryRepository.findAll(pageable);
    }

    /**
     * Get one heartRateSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HeartRateSummary> findOne(UUID id) {
        log.debug("Request to get HeartRateSummary : {}", id);
        return heartRateSummaryRepository.findById(id);
    }

    /**
     * Delete the heartRateSummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete HeartRateSummary : {}", id);
        heartRateSummaryRepository.deleteById(id);
    }
}
