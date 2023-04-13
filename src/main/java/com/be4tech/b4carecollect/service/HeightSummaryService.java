package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.HeightSummary;
import com.be4tech.b4carecollect.repository.HeightSummaryRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HeightSummary}.
 */
@Service
@Transactional
public class HeightSummaryService {

    private final Logger log = LoggerFactory.getLogger(HeightSummaryService.class);

    private final HeightSummaryRepository heightSummaryRepository;

    public HeightSummaryService(HeightSummaryRepository heightSummaryRepository) {
        this.heightSummaryRepository = heightSummaryRepository;
    }

    /**
     * Save a heightSummary.
     *
     * @param heightSummary the entity to save.
     * @return the persisted entity.
     */
    public HeightSummary save(HeightSummary heightSummary) {
        log.debug("Request to save HeightSummary : {}", heightSummary);
        return heightSummaryRepository.save(heightSummary);
    }

    /**
     * Update a heightSummary.
     *
     * @param heightSummary the entity to save.
     * @return the persisted entity.
     */
    public HeightSummary update(HeightSummary heightSummary) {
        log.debug("Request to update HeightSummary : {}", heightSummary);
        return heightSummaryRepository.save(heightSummary);
    }

    /**
     * Partially update a heightSummary.
     *
     * @param heightSummary the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HeightSummary> partialUpdate(HeightSummary heightSummary) {
        log.debug("Request to partially update HeightSummary : {}", heightSummary);

        return heightSummaryRepository
            .findById(heightSummary.getId())
            .map(existingHeightSummary -> {
                if (heightSummary.getUsuarioId() != null) {
                    existingHeightSummary.setUsuarioId(heightSummary.getUsuarioId());
                }
                if (heightSummary.getEmpresaId() != null) {
                    existingHeightSummary.setEmpresaId(heightSummary.getEmpresaId());
                }
                if (heightSummary.getFieldAverage() != null) {
                    existingHeightSummary.setFieldAverage(heightSummary.getFieldAverage());
                }
                if (heightSummary.getFieldMax() != null) {
                    existingHeightSummary.setFieldMax(heightSummary.getFieldMax());
                }
                if (heightSummary.getFieldMin() != null) {
                    existingHeightSummary.setFieldMin(heightSummary.getFieldMin());
                }
                if (heightSummary.getStartTime() != null) {
                    existingHeightSummary.setStartTime(heightSummary.getStartTime());
                }
                if (heightSummary.getEndTime() != null) {
                    existingHeightSummary.setEndTime(heightSummary.getEndTime());
                }

                return existingHeightSummary;
            })
            .map(heightSummaryRepository::save);
    }

    /**
     * Get all the heightSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HeightSummary> findAll(Pageable pageable) {
        log.debug("Request to get all HeightSummaries");
        return heightSummaryRepository.findAll(pageable);
    }

    /**
     * Get one heightSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HeightSummary> findOne(UUID id) {
        log.debug("Request to get HeightSummary : {}", id);
        return heightSummaryRepository.findById(id);
    }

    /**
     * Delete the heightSummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete HeightSummary : {}", id);
        heightSummaryRepository.deleteById(id);
    }
}
