package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.CaloriesExpended;
import com.be4tech.b4carecollect.repository.CaloriesExpendedRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CaloriesExpended}.
 */
@Service
@Transactional
public class CaloriesExpendedService {

    private final Logger log = LoggerFactory.getLogger(CaloriesExpendedService.class);

    private final CaloriesExpendedRepository caloriesExpendedRepository;

    public CaloriesExpendedService(CaloriesExpendedRepository caloriesExpendedRepository) {
        this.caloriesExpendedRepository = caloriesExpendedRepository;
    }

    /**
     * Save a caloriesExpended.
     *
     * @param caloriesExpended the entity to save.
     * @return the persisted entity.
     */
    public CaloriesExpended save(CaloriesExpended caloriesExpended) {
        log.debug("Request to save CaloriesExpended : {}", caloriesExpended);
        return caloriesExpendedRepository.save(caloriesExpended);
    }

    /**
     * Update a caloriesExpended.
     *
     * @param caloriesExpended the entity to save.
     * @return the persisted entity.
     */
    public CaloriesExpended update(CaloriesExpended caloriesExpended) {
        log.debug("Request to update CaloriesExpended : {}", caloriesExpended);
        return caloriesExpendedRepository.save(caloriesExpended);
    }

    /**
     * Partially update a caloriesExpended.
     *
     * @param caloriesExpended the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CaloriesExpended> partialUpdate(CaloriesExpended caloriesExpended) {
        log.debug("Request to partially update CaloriesExpended : {}", caloriesExpended);

        return caloriesExpendedRepository
            .findById(caloriesExpended.getId())
            .map(existingCaloriesExpended -> {
                if (caloriesExpended.getUsuarioId() != null) {
                    existingCaloriesExpended.setUsuarioId(caloriesExpended.getUsuarioId());
                }
                if (caloriesExpended.getEmpresaId() != null) {
                    existingCaloriesExpended.setEmpresaId(caloriesExpended.getEmpresaId());
                }
                if (caloriesExpended.getCalorias() != null) {
                    existingCaloriesExpended.setCalorias(caloriesExpended.getCalorias());
                }
                if (caloriesExpended.getStartTime() != null) {
                    existingCaloriesExpended.setStartTime(caloriesExpended.getStartTime());
                }
                if (caloriesExpended.getEndTime() != null) {
                    existingCaloriesExpended.setEndTime(caloriesExpended.getEndTime());
                }

                return existingCaloriesExpended;
            })
            .map(caloriesExpendedRepository::save);
    }

    /**
     * Get all the caloriesExpendeds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CaloriesExpended> findAll(Pageable pageable) {
        log.debug("Request to get all CaloriesExpendeds");
        return caloriesExpendedRepository.findAll(pageable);
    }

    /**
     * Get one caloriesExpended by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CaloriesExpended> findOne(UUID id) {
        log.debug("Request to get CaloriesExpended : {}", id);
        return caloriesExpendedRepository.findById(id);
    }

    /**
     * Delete the caloriesExpended by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete CaloriesExpended : {}", id);
        caloriesExpendedRepository.deleteById(id);
    }
}
