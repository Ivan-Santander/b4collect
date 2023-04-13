package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.SleepScores;
import com.be4tech.b4carecollect.repository.SleepScoresRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SleepScores}.
 */
@Service
@Transactional
public class SleepScoresService {

    private final Logger log = LoggerFactory.getLogger(SleepScoresService.class);

    private final SleepScoresRepository sleepScoresRepository;

    public SleepScoresService(SleepScoresRepository sleepScoresRepository) {
        this.sleepScoresRepository = sleepScoresRepository;
    }

    /**
     * Save a sleepScores.
     *
     * @param sleepScores the entity to save.
     * @return the persisted entity.
     */
    public SleepScores save(SleepScores sleepScores) {
        log.debug("Request to save SleepScores : {}", sleepScores);
        return sleepScoresRepository.save(sleepScores);
    }

    /**
     * Update a sleepScores.
     *
     * @param sleepScores the entity to save.
     * @return the persisted entity.
     */
    public SleepScores update(SleepScores sleepScores) {
        log.debug("Request to update SleepScores : {}", sleepScores);
        return sleepScoresRepository.save(sleepScores);
    }

    /**
     * Partially update a sleepScores.
     *
     * @param sleepScores the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SleepScores> partialUpdate(SleepScores sleepScores) {
        log.debug("Request to partially update SleepScores : {}", sleepScores);

        return sleepScoresRepository
            .findById(sleepScores.getId())
            .map(existingSleepScores -> {
                if (sleepScores.getUsuarioId() != null) {
                    existingSleepScores.setUsuarioId(sleepScores.getUsuarioId());
                }
                if (sleepScores.getEmpresaId() != null) {
                    existingSleepScores.setEmpresaId(sleepScores.getEmpresaId());
                }
                if (sleepScores.getSleepQualityRatingScore() != null) {
                    existingSleepScores.setSleepQualityRatingScore(sleepScores.getSleepQualityRatingScore());
                }
                if (sleepScores.getSleepEfficiencyScore() != null) {
                    existingSleepScores.setSleepEfficiencyScore(sleepScores.getSleepEfficiencyScore());
                }
                if (sleepScores.getSleepGooalSecondsScore() != null) {
                    existingSleepScores.setSleepGooalSecondsScore(sleepScores.getSleepGooalSecondsScore());
                }
                if (sleepScores.getSleepContinuityScore() != null) {
                    existingSleepScores.setSleepContinuityScore(sleepScores.getSleepContinuityScore());
                }
                if (sleepScores.getSleepContinuityRating() != null) {
                    existingSleepScores.setSleepContinuityRating(sleepScores.getSleepContinuityRating());
                }

                return existingSleepScores;
            })
            .map(sleepScoresRepository::save);
    }

    /**
     * Get all the sleepScores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SleepScores> findAll(Pageable pageable) {
        log.debug("Request to get all SleepScores");
        return sleepScoresRepository.findAll(pageable);
    }

    /**
     * Get one sleepScores by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SleepScores> findOne(UUID id) {
        log.debug("Request to get SleepScores : {}", id);
        return sleepScoresRepository.findById(id);
    }

    /**
     * Delete the sleepScores by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete SleepScores : {}", id);
        sleepScoresRepository.deleteById(id);
    }
}
