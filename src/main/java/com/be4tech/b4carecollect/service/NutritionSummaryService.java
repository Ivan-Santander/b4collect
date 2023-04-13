package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.NutritionSummary;
import com.be4tech.b4carecollect.repository.NutritionSummaryRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NutritionSummary}.
 */
@Service
@Transactional
public class NutritionSummaryService {

    private final Logger log = LoggerFactory.getLogger(NutritionSummaryService.class);

    private final NutritionSummaryRepository nutritionSummaryRepository;

    public NutritionSummaryService(NutritionSummaryRepository nutritionSummaryRepository) {
        this.nutritionSummaryRepository = nutritionSummaryRepository;
    }

    /**
     * Save a nutritionSummary.
     *
     * @param nutritionSummary the entity to save.
     * @return the persisted entity.
     */
    public NutritionSummary save(NutritionSummary nutritionSummary) {
        log.debug("Request to save NutritionSummary : {}", nutritionSummary);
        return nutritionSummaryRepository.save(nutritionSummary);
    }

    /**
     * Update a nutritionSummary.
     *
     * @param nutritionSummary the entity to save.
     * @return the persisted entity.
     */
    public NutritionSummary update(NutritionSummary nutritionSummary) {
        log.debug("Request to update NutritionSummary : {}", nutritionSummary);
        return nutritionSummaryRepository.save(nutritionSummary);
    }

    /**
     * Partially update a nutritionSummary.
     *
     * @param nutritionSummary the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NutritionSummary> partialUpdate(NutritionSummary nutritionSummary) {
        log.debug("Request to partially update NutritionSummary : {}", nutritionSummary);

        return nutritionSummaryRepository
            .findById(nutritionSummary.getId())
            .map(existingNutritionSummary -> {
                if (nutritionSummary.getUsuarioId() != null) {
                    existingNutritionSummary.setUsuarioId(nutritionSummary.getUsuarioId());
                }
                if (nutritionSummary.getEmpresaId() != null) {
                    existingNutritionSummary.setEmpresaId(nutritionSummary.getEmpresaId());
                }
                if (nutritionSummary.getMealType() != null) {
                    existingNutritionSummary.setMealType(nutritionSummary.getMealType());
                }
                if (nutritionSummary.getNutrient() != null) {
                    existingNutritionSummary.setNutrient(nutritionSummary.getNutrient());
                }
                if (nutritionSummary.getStartTime() != null) {
                    existingNutritionSummary.setStartTime(nutritionSummary.getStartTime());
                }
                if (nutritionSummary.getEndTime() != null) {
                    existingNutritionSummary.setEndTime(nutritionSummary.getEndTime());
                }

                return existingNutritionSummary;
            })
            .map(nutritionSummaryRepository::save);
    }

    /**
     * Get all the nutritionSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NutritionSummary> findAll(Pageable pageable) {
        log.debug("Request to get all NutritionSummaries");
        return nutritionSummaryRepository.findAll(pageable);
    }

    /**
     * Get one nutritionSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NutritionSummary> findOne(UUID id) {
        log.debug("Request to get NutritionSummary : {}", id);
        return nutritionSummaryRepository.findById(id);
    }

    /**
     * Delete the nutritionSummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete NutritionSummary : {}", id);
        nutritionSummaryRepository.deleteById(id);
    }
}
