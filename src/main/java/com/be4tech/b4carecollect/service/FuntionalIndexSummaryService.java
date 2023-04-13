package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.FuntionalIndexSummary;
import com.be4tech.b4carecollect.repository.FuntionalIndexSummaryRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FuntionalIndexSummary}.
 */
@Service
@Transactional
public class FuntionalIndexSummaryService {

    private final Logger log = LoggerFactory.getLogger(FuntionalIndexSummaryService.class);

    private final FuntionalIndexSummaryRepository funtionalIndexSummaryRepository;

    public FuntionalIndexSummaryService(FuntionalIndexSummaryRepository funtionalIndexSummaryRepository) {
        this.funtionalIndexSummaryRepository = funtionalIndexSummaryRepository;
    }

    /**
     * Save a funtionalIndexSummary.
     *
     * @param funtionalIndexSummary the entity to save.
     * @return the persisted entity.
     */
    public FuntionalIndexSummary save(FuntionalIndexSummary funtionalIndexSummary) {
        log.debug("Request to save FuntionalIndexSummary : {}", funtionalIndexSummary);
        return funtionalIndexSummaryRepository.save(funtionalIndexSummary);
    }

    /**
     * Update a funtionalIndexSummary.
     *
     * @param funtionalIndexSummary the entity to save.
     * @return the persisted entity.
     */
    public FuntionalIndexSummary update(FuntionalIndexSummary funtionalIndexSummary) {
        log.debug("Request to update FuntionalIndexSummary : {}", funtionalIndexSummary);
        return funtionalIndexSummaryRepository.save(funtionalIndexSummary);
    }

    /**
     * Partially update a funtionalIndexSummary.
     *
     * @param funtionalIndexSummary the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FuntionalIndexSummary> partialUpdate(FuntionalIndexSummary funtionalIndexSummary) {
        log.debug("Request to partially update FuntionalIndexSummary : {}", funtionalIndexSummary);

        return funtionalIndexSummaryRepository
            .findById(funtionalIndexSummary.getId())
            .map(existingFuntionalIndexSummary -> {
                if (funtionalIndexSummary.getUsuarioId() != null) {
                    existingFuntionalIndexSummary.setUsuarioId(funtionalIndexSummary.getUsuarioId());
                }
                if (funtionalIndexSummary.getEmpresaId() != null) {
                    existingFuntionalIndexSummary.setEmpresaId(funtionalIndexSummary.getEmpresaId());
                }
                if (funtionalIndexSummary.getFieldFuntionalIndexAverage() != null) {
                    existingFuntionalIndexSummary.setFieldFuntionalIndexAverage(funtionalIndexSummary.getFieldFuntionalIndexAverage());
                }
                if (funtionalIndexSummary.getFieldFuntionalIndexMax() != null) {
                    existingFuntionalIndexSummary.setFieldFuntionalIndexMax(funtionalIndexSummary.getFieldFuntionalIndexMax());
                }
                if (funtionalIndexSummary.getFieldFuntionalIndexMin() != null) {
                    existingFuntionalIndexSummary.setFieldFuntionalIndexMin(funtionalIndexSummary.getFieldFuntionalIndexMin());
                }
                if (funtionalIndexSummary.getStartTime() != null) {
                    existingFuntionalIndexSummary.setStartTime(funtionalIndexSummary.getStartTime());
                }
                if (funtionalIndexSummary.getEndTime() != null) {
                    existingFuntionalIndexSummary.setEndTime(funtionalIndexSummary.getEndTime());
                }

                return existingFuntionalIndexSummary;
            })
            .map(funtionalIndexSummaryRepository::save);
    }

    /**
     * Get all the funtionalIndexSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FuntionalIndexSummary> findAll(Pageable pageable) {
        log.debug("Request to get all FuntionalIndexSummaries");
        return funtionalIndexSummaryRepository.findAll(pageable);
    }

    /**
     * Get one funtionalIndexSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FuntionalIndexSummary> findOne(UUID id) {
        log.debug("Request to get FuntionalIndexSummary : {}", id);
        return funtionalIndexSummaryRepository.findById(id);
    }

    /**
     * Delete the funtionalIndexSummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete FuntionalIndexSummary : {}", id);
        funtionalIndexSummaryRepository.deleteById(id);
    }
}
