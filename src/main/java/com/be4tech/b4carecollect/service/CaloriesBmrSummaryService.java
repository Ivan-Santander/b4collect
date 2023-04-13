package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.CaloriesBmrSummary;
import com.be4tech.b4carecollect.repository.CaloriesBmrSummaryRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CaloriesBmrSummary}.
 */
@Service
@Transactional
public class CaloriesBmrSummaryService {

    private final Logger log = LoggerFactory.getLogger(CaloriesBmrSummaryService.class);

    private final CaloriesBmrSummaryRepository caloriesBmrSummaryRepository;

    public CaloriesBmrSummaryService(CaloriesBmrSummaryRepository caloriesBmrSummaryRepository) {
        this.caloriesBmrSummaryRepository = caloriesBmrSummaryRepository;
    }

    /**
     * Save a caloriesBmrSummary.
     *
     * @param caloriesBmrSummary the entity to save.
     * @return the persisted entity.
     */
    public CaloriesBmrSummary save(CaloriesBmrSummary caloriesBmrSummary) {
        log.debug("Request to save CaloriesBmrSummary : {}", caloriesBmrSummary);
        return caloriesBmrSummaryRepository.save(caloriesBmrSummary);
    }

    /**
     * Update a caloriesBmrSummary.
     *
     * @param caloriesBmrSummary the entity to save.
     * @return the persisted entity.
     */
    public CaloriesBmrSummary update(CaloriesBmrSummary caloriesBmrSummary) {
        log.debug("Request to update CaloriesBmrSummary : {}", caloriesBmrSummary);
        return caloriesBmrSummaryRepository.save(caloriesBmrSummary);
    }

    /**
     * Partially update a caloriesBmrSummary.
     *
     * @param caloriesBmrSummary the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CaloriesBmrSummary> partialUpdate(CaloriesBmrSummary caloriesBmrSummary) {
        log.debug("Request to partially update CaloriesBmrSummary : {}", caloriesBmrSummary);

        return caloriesBmrSummaryRepository
            .findById(caloriesBmrSummary.getId())
            .map(existingCaloriesBmrSummary -> {
                if (caloriesBmrSummary.getUsuarioId() != null) {
                    existingCaloriesBmrSummary.setUsuarioId(caloriesBmrSummary.getUsuarioId());
                }
                if (caloriesBmrSummary.getEmpresaId() != null) {
                    existingCaloriesBmrSummary.setEmpresaId(caloriesBmrSummary.getEmpresaId());
                }
                if (caloriesBmrSummary.getFieldAverage() != null) {
                    existingCaloriesBmrSummary.setFieldAverage(caloriesBmrSummary.getFieldAverage());
                }
                if (caloriesBmrSummary.getFieldMax() != null) {
                    existingCaloriesBmrSummary.setFieldMax(caloriesBmrSummary.getFieldMax());
                }
                if (caloriesBmrSummary.getFieldMin() != null) {
                    existingCaloriesBmrSummary.setFieldMin(caloriesBmrSummary.getFieldMin());
                }
                if (caloriesBmrSummary.getStartTime() != null) {
                    existingCaloriesBmrSummary.setStartTime(caloriesBmrSummary.getStartTime());
                }
                if (caloriesBmrSummary.getEndTime() != null) {
                    existingCaloriesBmrSummary.setEndTime(caloriesBmrSummary.getEndTime());
                }

                return existingCaloriesBmrSummary;
            })
            .map(caloriesBmrSummaryRepository::save);
    }

    /**
     * Get all the caloriesBmrSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CaloriesBmrSummary> findAll(Pageable pageable) {
        log.debug("Request to get all CaloriesBmrSummaries");
        return caloriesBmrSummaryRepository.findAll(pageable);
    }

    /**
     * Get one caloriesBmrSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CaloriesBmrSummary> findOne(UUID id) {
        log.debug("Request to get CaloriesBmrSummary : {}", id);
        return caloriesBmrSummaryRepository.findById(id);
    }

    /**
     * Delete the caloriesBmrSummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete CaloriesBmrSummary : {}", id);
        caloriesBmrSummaryRepository.deleteById(id);
    }
}
