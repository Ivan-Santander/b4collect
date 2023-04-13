package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.BodyFatPercentageSummary;
import com.be4tech.b4carecollect.repository.BodyFatPercentageSummaryRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BodyFatPercentageSummary}.
 */
@Service
@Transactional
public class BodyFatPercentageSummaryService {

    private final Logger log = LoggerFactory.getLogger(BodyFatPercentageSummaryService.class);

    private final BodyFatPercentageSummaryRepository bodyFatPercentageSummaryRepository;

    public BodyFatPercentageSummaryService(BodyFatPercentageSummaryRepository bodyFatPercentageSummaryRepository) {
        this.bodyFatPercentageSummaryRepository = bodyFatPercentageSummaryRepository;
    }

    /**
     * Save a bodyFatPercentageSummary.
     *
     * @param bodyFatPercentageSummary the entity to save.
     * @return the persisted entity.
     */
    public BodyFatPercentageSummary save(BodyFatPercentageSummary bodyFatPercentageSummary) {
        log.debug("Request to save BodyFatPercentageSummary : {}", bodyFatPercentageSummary);
        return bodyFatPercentageSummaryRepository.save(bodyFatPercentageSummary);
    }

    /**
     * Update a bodyFatPercentageSummary.
     *
     * @param bodyFatPercentageSummary the entity to save.
     * @return the persisted entity.
     */
    public BodyFatPercentageSummary update(BodyFatPercentageSummary bodyFatPercentageSummary) {
        log.debug("Request to update BodyFatPercentageSummary : {}", bodyFatPercentageSummary);
        return bodyFatPercentageSummaryRepository.save(bodyFatPercentageSummary);
    }

    /**
     * Partially update a bodyFatPercentageSummary.
     *
     * @param bodyFatPercentageSummary the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BodyFatPercentageSummary> partialUpdate(BodyFatPercentageSummary bodyFatPercentageSummary) {
        log.debug("Request to partially update BodyFatPercentageSummary : {}", bodyFatPercentageSummary);

        return bodyFatPercentageSummaryRepository
            .findById(bodyFatPercentageSummary.getId())
            .map(existingBodyFatPercentageSummary -> {
                if (bodyFatPercentageSummary.getUsuarioId() != null) {
                    existingBodyFatPercentageSummary.setUsuarioId(bodyFatPercentageSummary.getUsuarioId());
                }
                if (bodyFatPercentageSummary.getEmpresaId() != null) {
                    existingBodyFatPercentageSummary.setEmpresaId(bodyFatPercentageSummary.getEmpresaId());
                }
                if (bodyFatPercentageSummary.getFieldAverage() != null) {
                    existingBodyFatPercentageSummary.setFieldAverage(bodyFatPercentageSummary.getFieldAverage());
                }
                if (bodyFatPercentageSummary.getFieldMax() != null) {
                    existingBodyFatPercentageSummary.setFieldMax(bodyFatPercentageSummary.getFieldMax());
                }
                if (bodyFatPercentageSummary.getFieldMin() != null) {
                    existingBodyFatPercentageSummary.setFieldMin(bodyFatPercentageSummary.getFieldMin());
                }
                if (bodyFatPercentageSummary.getStartTime() != null) {
                    existingBodyFatPercentageSummary.setStartTime(bodyFatPercentageSummary.getStartTime());
                }
                if (bodyFatPercentageSummary.getEndTime() != null) {
                    existingBodyFatPercentageSummary.setEndTime(bodyFatPercentageSummary.getEndTime());
                }

                return existingBodyFatPercentageSummary;
            })
            .map(bodyFatPercentageSummaryRepository::save);
    }

    /**
     * Get all the bodyFatPercentageSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BodyFatPercentageSummary> findAll(Pageable pageable) {
        log.debug("Request to get all BodyFatPercentageSummaries");
        return bodyFatPercentageSummaryRepository.findAll(pageable);
    }

    /**
     * Get one bodyFatPercentageSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BodyFatPercentageSummary> findOne(UUID id) {
        log.debug("Request to get BodyFatPercentageSummary : {}", id);
        return bodyFatPercentageSummaryRepository.findById(id);
    }

    /**
     * Delete the bodyFatPercentageSummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete BodyFatPercentageSummary : {}", id);
        bodyFatPercentageSummaryRepository.deleteById(id);
    }
}
