package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.Nutrition;
import com.be4tech.b4carecollect.repository.NutritionRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Nutrition}.
 */
@Service
@Transactional
public class NutritionService {

    private final Logger log = LoggerFactory.getLogger(NutritionService.class);

    private final NutritionRepository nutritionRepository;

    public NutritionService(NutritionRepository nutritionRepository) {
        this.nutritionRepository = nutritionRepository;
    }

    /**
     * Save a nutrition.
     *
     * @param nutrition the entity to save.
     * @return the persisted entity.
     */
    public Nutrition save(Nutrition nutrition) {
        log.debug("Request to save Nutrition : {}", nutrition);
        return nutritionRepository.save(nutrition);
    }

    /**
     * Update a nutrition.
     *
     * @param nutrition the entity to save.
     * @return the persisted entity.
     */
    public Nutrition update(Nutrition nutrition) {
        log.debug("Request to update Nutrition : {}", nutrition);
        return nutritionRepository.save(nutrition);
    }

    /**
     * Partially update a nutrition.
     *
     * @param nutrition the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Nutrition> partialUpdate(Nutrition nutrition) {
        log.debug("Request to partially update Nutrition : {}", nutrition);

        return nutritionRepository
            .findById(nutrition.getId())
            .map(existingNutrition -> {
                if (nutrition.getUsuarioId() != null) {
                    existingNutrition.setUsuarioId(nutrition.getUsuarioId());
                }
                if (nutrition.getEmpresaId() != null) {
                    existingNutrition.setEmpresaId(nutrition.getEmpresaId());
                }
                if (nutrition.getMealType() != null) {
                    existingNutrition.setMealType(nutrition.getMealType());
                }
                if (nutrition.getFood() != null) {
                    existingNutrition.setFood(nutrition.getFood());
                }
                if (nutrition.getNutrients() != null) {
                    existingNutrition.setNutrients(nutrition.getNutrients());
                }
                if (nutrition.getEndTime() != null) {
                    existingNutrition.setEndTime(nutrition.getEndTime());
                }

                return existingNutrition;
            })
            .map(nutritionRepository::save);
    }

    /**
     * Get all the nutritions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Nutrition> findAll(Pageable pageable) {
        log.debug("Request to get all Nutritions");
        return nutritionRepository.findAll(pageable);
    }

    /**
     * Get one nutrition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Nutrition> findOne(UUID id) {
        log.debug("Request to get Nutrition : {}", id);
        return nutritionRepository.findById(id);
    }

    /**
     * Delete the nutrition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Nutrition : {}", id);
        nutritionRepository.deleteById(id);
    }
}
