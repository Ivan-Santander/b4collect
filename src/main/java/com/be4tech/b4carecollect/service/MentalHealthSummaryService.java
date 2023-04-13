package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.MentalHealthSummary;
import com.be4tech.b4carecollect.repository.MentalHealthSummaryRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MentalHealthSummary}.
 */
@Service
@Transactional
public class MentalHealthSummaryService {

    private final Logger log = LoggerFactory.getLogger(MentalHealthSummaryService.class);

    private final MentalHealthSummaryRepository mentalHealthSummaryRepository;

    public MentalHealthSummaryService(MentalHealthSummaryRepository mentalHealthSummaryRepository) {
        this.mentalHealthSummaryRepository = mentalHealthSummaryRepository;
    }

    /**
     * Save a mentalHealthSummary.
     *
     * @param mentalHealthSummary the entity to save.
     * @return the persisted entity.
     */
    public MentalHealthSummary save(MentalHealthSummary mentalHealthSummary) {
        log.debug("Request to save MentalHealthSummary : {}", mentalHealthSummary);
        return mentalHealthSummaryRepository.save(mentalHealthSummary);
    }

    /**
     * Update a mentalHealthSummary.
     *
     * @param mentalHealthSummary the entity to save.
     * @return the persisted entity.
     */
    public MentalHealthSummary update(MentalHealthSummary mentalHealthSummary) {
        log.debug("Request to update MentalHealthSummary : {}", mentalHealthSummary);
        return mentalHealthSummaryRepository.save(mentalHealthSummary);
    }

    /**
     * Partially update a mentalHealthSummary.
     *
     * @param mentalHealthSummary the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MentalHealthSummary> partialUpdate(MentalHealthSummary mentalHealthSummary) {
        log.debug("Request to partially update MentalHealthSummary : {}", mentalHealthSummary);

        return mentalHealthSummaryRepository
            .findById(mentalHealthSummary.getId())
            .map(existingMentalHealthSummary -> {
                if (mentalHealthSummary.getUsuarioId() != null) {
                    existingMentalHealthSummary.setUsuarioId(mentalHealthSummary.getUsuarioId());
                }
                if (mentalHealthSummary.getEmpresaId() != null) {
                    existingMentalHealthSummary.setEmpresaId(mentalHealthSummary.getEmpresaId());
                }
                if (mentalHealthSummary.getEmotionDescripMain() != null) {
                    existingMentalHealthSummary.setEmotionDescripMain(mentalHealthSummary.getEmotionDescripMain());
                }
                if (mentalHealthSummary.getEmotionDescripSecond() != null) {
                    existingMentalHealthSummary.setEmotionDescripSecond(mentalHealthSummary.getEmotionDescripSecond());
                }
                if (mentalHealthSummary.getFieldMentalHealthAverage() != null) {
                    existingMentalHealthSummary.setFieldMentalHealthAverage(mentalHealthSummary.getFieldMentalHealthAverage());
                }
                if (mentalHealthSummary.getFieldMentalHealthMax() != null) {
                    existingMentalHealthSummary.setFieldMentalHealthMax(mentalHealthSummary.getFieldMentalHealthMax());
                }
                if (mentalHealthSummary.getFieldMentalHealthMin() != null) {
                    existingMentalHealthSummary.setFieldMentalHealthMin(mentalHealthSummary.getFieldMentalHealthMin());
                }
                if (mentalHealthSummary.getScoreMentalRisk() != null) {
                    existingMentalHealthSummary.setScoreMentalRisk(mentalHealthSummary.getScoreMentalRisk());
                }
                if (mentalHealthSummary.getStartTime() != null) {
                    existingMentalHealthSummary.setStartTime(mentalHealthSummary.getStartTime());
                }
                if (mentalHealthSummary.getEndTime() != null) {
                    existingMentalHealthSummary.setEndTime(mentalHealthSummary.getEndTime());
                }

                return existingMentalHealthSummary;
            })
            .map(mentalHealthSummaryRepository::save);
    }

    /**
     * Get all the mentalHealthSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MentalHealthSummary> findAll(Pageable pageable) {
        log.debug("Request to get all MentalHealthSummaries");
        return mentalHealthSummaryRepository.findAll(pageable);
    }

    /**
     * Get one mentalHealthSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MentalHealthSummary> findOne(UUID id) {
        log.debug("Request to get MentalHealthSummary : {}", id);
        return mentalHealthSummaryRepository.findById(id);
    }

    /**
     * Delete the mentalHealthSummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete MentalHealthSummary : {}", id);
        mentalHealthSummaryRepository.deleteById(id);
    }
}
