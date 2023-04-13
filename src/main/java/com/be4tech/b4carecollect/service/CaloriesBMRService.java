package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.CaloriesBMR;
import com.be4tech.b4carecollect.repository.CaloriesBMRRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CaloriesBMR}.
 */
@Service
@Transactional
public class CaloriesBMRService {

    private final Logger log = LoggerFactory.getLogger(CaloriesBMRService.class);

    private final CaloriesBMRRepository caloriesBMRRepository;

    public CaloriesBMRService(CaloriesBMRRepository caloriesBMRRepository) {
        this.caloriesBMRRepository = caloriesBMRRepository;
    }

    /**
     * Save a caloriesBMR.
     *
     * @param caloriesBMR the entity to save.
     * @return the persisted entity.
     */
    public CaloriesBMR save(CaloriesBMR caloriesBMR) {
        log.debug("Request to save CaloriesBMR : {}", caloriesBMR);
        return caloriesBMRRepository.save(caloriesBMR);
    }

    /**
     * Update a caloriesBMR.
     *
     * @param caloriesBMR the entity to save.
     * @return the persisted entity.
     */
    public CaloriesBMR update(CaloriesBMR caloriesBMR) {
        log.debug("Request to update CaloriesBMR : {}", caloriesBMR);
        return caloriesBMRRepository.save(caloriesBMR);
    }

    /**
     * Partially update a caloriesBMR.
     *
     * @param caloriesBMR the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CaloriesBMR> partialUpdate(CaloriesBMR caloriesBMR) {
        log.debug("Request to partially update CaloriesBMR : {}", caloriesBMR);

        return caloriesBMRRepository
            .findById(caloriesBMR.getId())
            .map(existingCaloriesBMR -> {
                if (caloriesBMR.getUsuarioId() != null) {
                    existingCaloriesBMR.setUsuarioId(caloriesBMR.getUsuarioId());
                }
                if (caloriesBMR.getEmpresaId() != null) {
                    existingCaloriesBMR.setEmpresaId(caloriesBMR.getEmpresaId());
                }
                if (caloriesBMR.getCalorias() != null) {
                    existingCaloriesBMR.setCalorias(caloriesBMR.getCalorias());
                }
                if (caloriesBMR.getStartTime() != null) {
                    existingCaloriesBMR.setStartTime(caloriesBMR.getStartTime());
                }
                if (caloriesBMR.getEndTime() != null) {
                    existingCaloriesBMR.setEndTime(caloriesBMR.getEndTime());
                }

                return existingCaloriesBMR;
            })
            .map(caloriesBMRRepository::save);
    }

    /**
     * Get all the caloriesBMRS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CaloriesBMR> findAll(Pageable pageable) {
        log.debug("Request to get all CaloriesBMRS");
        return caloriesBMRRepository.findAll(pageable);
    }

    /**
     * Get one caloriesBMR by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CaloriesBMR> findOne(UUID id) {
        log.debug("Request to get CaloriesBMR : {}", id);
        return caloriesBMRRepository.findById(id);
    }

    /**
     * Delete the caloriesBMR by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete CaloriesBMR : {}", id);
        caloriesBMRRepository.deleteById(id);
    }
}
