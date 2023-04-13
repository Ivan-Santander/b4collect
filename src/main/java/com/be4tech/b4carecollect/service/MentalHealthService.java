package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.MentalHealth;
import com.be4tech.b4carecollect.repository.MentalHealthRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MentalHealth}.
 */
@Service
@Transactional
public class MentalHealthService {

    private final Logger log = LoggerFactory.getLogger(MentalHealthService.class);

    private final MentalHealthRepository mentalHealthRepository;

    public MentalHealthService(MentalHealthRepository mentalHealthRepository) {
        this.mentalHealthRepository = mentalHealthRepository;
    }

    /**
     * Save a mentalHealth.
     *
     * @param mentalHealth the entity to save.
     * @return the persisted entity.
     */
    public MentalHealth save(MentalHealth mentalHealth) {
        log.debug("Request to save MentalHealth : {}", mentalHealth);
        return mentalHealthRepository.save(mentalHealth);
    }

    /**
     * Update a mentalHealth.
     *
     * @param mentalHealth the entity to save.
     * @return the persisted entity.
     */
    public MentalHealth update(MentalHealth mentalHealth) {
        log.debug("Request to update MentalHealth : {}", mentalHealth);
        return mentalHealthRepository.save(mentalHealth);
    }

    /**
     * Partially update a mentalHealth.
     *
     * @param mentalHealth the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MentalHealth> partialUpdate(MentalHealth mentalHealth) {
        log.debug("Request to partially update MentalHealth : {}", mentalHealth);

        return mentalHealthRepository
            .findById(mentalHealth.getId())
            .map(existingMentalHealth -> {
                if (mentalHealth.getUsuarioId() != null) {
                    existingMentalHealth.setUsuarioId(mentalHealth.getUsuarioId());
                }
                if (mentalHealth.getEmpresaId() != null) {
                    existingMentalHealth.setEmpresaId(mentalHealth.getEmpresaId());
                }
                if (mentalHealth.getEmotionDescription() != null) {
                    existingMentalHealth.setEmotionDescription(mentalHealth.getEmotionDescription());
                }
                if (mentalHealth.getEmotionValue() != null) {
                    existingMentalHealth.setEmotionValue(mentalHealth.getEmotionValue());
                }
                if (mentalHealth.getStartDate() != null) {
                    existingMentalHealth.setStartDate(mentalHealth.getStartDate());
                }
                if (mentalHealth.getEndDate() != null) {
                    existingMentalHealth.setEndDate(mentalHealth.getEndDate());
                }
                if (mentalHealth.getMentalHealthScore() != null) {
                    existingMentalHealth.setMentalHealthScore(mentalHealth.getMentalHealthScore());
                }

                return existingMentalHealth;
            })
            .map(mentalHealthRepository::save);
    }

    /**
     * Get all the mentalHealths.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MentalHealth> findAll(Pageable pageable) {
        log.debug("Request to get all MentalHealths");
        return mentalHealthRepository.findAll(pageable);
    }

    /**
     * Get one mentalHealth by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MentalHealth> findOne(UUID id) {
        log.debug("Request to get MentalHealth : {}", id);
        return mentalHealthRepository.findById(id);
    }

    /**
     * Delete the mentalHealth by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete MentalHealth : {}", id);
        mentalHealthRepository.deleteById(id);
    }
}
