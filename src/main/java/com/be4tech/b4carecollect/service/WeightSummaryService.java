package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.WeightSummary;
import com.be4tech.b4carecollect.repository.WeightSummaryRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WeightSummary}.
 */
@Service
@Transactional
public class WeightSummaryService {

    private final Logger log = LoggerFactory.getLogger(WeightSummaryService.class);

    private final WeightSummaryRepository weightSummaryRepository;

    public WeightSummaryService(WeightSummaryRepository weightSummaryRepository) {
        this.weightSummaryRepository = weightSummaryRepository;
    }

    /**
     * Save a weightSummary.
     *
     * @param weightSummary the entity to save.
     * @return the persisted entity.
     */
    public WeightSummary save(WeightSummary weightSummary) {
        log.debug("Request to save WeightSummary : {}", weightSummary);
        return weightSummaryRepository.save(weightSummary);
    }

    /**
     * Update a weightSummary.
     *
     * @param weightSummary the entity to save.
     * @return the persisted entity.
     */
    public WeightSummary update(WeightSummary weightSummary) {
        log.debug("Request to update WeightSummary : {}", weightSummary);
        return weightSummaryRepository.save(weightSummary);
    }

    /**
     * Partially update a weightSummary.
     *
     * @param weightSummary the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WeightSummary> partialUpdate(WeightSummary weightSummary) {
        log.debug("Request to partially update WeightSummary : {}", weightSummary);

        return weightSummaryRepository
            .findById(weightSummary.getId())
            .map(existingWeightSummary -> {
                if (weightSummary.getUsuarioId() != null) {
                    existingWeightSummary.setUsuarioId(weightSummary.getUsuarioId());
                }
                if (weightSummary.getEmpresaId() != null) {
                    existingWeightSummary.setEmpresaId(weightSummary.getEmpresaId());
                }
                if (weightSummary.getFieldAverage() != null) {
                    existingWeightSummary.setFieldAverage(weightSummary.getFieldAverage());
                }
                if (weightSummary.getFieldMax() != null) {
                    existingWeightSummary.setFieldMax(weightSummary.getFieldMax());
                }
                if (weightSummary.getFieldMin() != null) {
                    existingWeightSummary.setFieldMin(weightSummary.getFieldMin());
                }
                if (weightSummary.getStartTime() != null) {
                    existingWeightSummary.setStartTime(weightSummary.getStartTime());
                }
                if (weightSummary.getEndTime() != null) {
                    existingWeightSummary.setEndTime(weightSummary.getEndTime());
                }

                return existingWeightSummary;
            })
            .map(weightSummaryRepository::save);
    }

    /**
     * Get all the weightSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WeightSummary> findAll(Pageable pageable) {
        log.debug("Request to get all WeightSummaries");
        return weightSummaryRepository.findAll(pageable);
    }

    /**
     * Get one weightSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WeightSummary> findOne(UUID id) {
        log.debug("Request to get WeightSummary : {}", id);
        return weightSummaryRepository.findById(id);
    }

    /**
     * Delete the weightSummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete WeightSummary : {}", id);
        weightSummaryRepository.deleteById(id);
    }
}
