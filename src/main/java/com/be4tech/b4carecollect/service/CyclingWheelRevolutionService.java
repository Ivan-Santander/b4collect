package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.CyclingWheelRevolution;
import com.be4tech.b4carecollect.repository.CyclingWheelRevolutionRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CyclingWheelRevolution}.
 */
@Service
@Transactional
public class CyclingWheelRevolutionService {

    private final Logger log = LoggerFactory.getLogger(CyclingWheelRevolutionService.class);

    private final CyclingWheelRevolutionRepository cyclingWheelRevolutionRepository;

    public CyclingWheelRevolutionService(CyclingWheelRevolutionRepository cyclingWheelRevolutionRepository) {
        this.cyclingWheelRevolutionRepository = cyclingWheelRevolutionRepository;
    }

    /**
     * Save a cyclingWheelRevolution.
     *
     * @param cyclingWheelRevolution the entity to save.
     * @return the persisted entity.
     */
    public CyclingWheelRevolution save(CyclingWheelRevolution cyclingWheelRevolution) {
        log.debug("Request to save CyclingWheelRevolution : {}", cyclingWheelRevolution);
        return cyclingWheelRevolutionRepository.save(cyclingWheelRevolution);
    }

    /**
     * Update a cyclingWheelRevolution.
     *
     * @param cyclingWheelRevolution the entity to save.
     * @return the persisted entity.
     */
    public CyclingWheelRevolution update(CyclingWheelRevolution cyclingWheelRevolution) {
        log.debug("Request to update CyclingWheelRevolution : {}", cyclingWheelRevolution);
        return cyclingWheelRevolutionRepository.save(cyclingWheelRevolution);
    }

    /**
     * Partially update a cyclingWheelRevolution.
     *
     * @param cyclingWheelRevolution the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CyclingWheelRevolution> partialUpdate(CyclingWheelRevolution cyclingWheelRevolution) {
        log.debug("Request to partially update CyclingWheelRevolution : {}", cyclingWheelRevolution);

        return cyclingWheelRevolutionRepository
            .findById(cyclingWheelRevolution.getId())
            .map(existingCyclingWheelRevolution -> {
                if (cyclingWheelRevolution.getUsuarioId() != null) {
                    existingCyclingWheelRevolution.setUsuarioId(cyclingWheelRevolution.getUsuarioId());
                }
                if (cyclingWheelRevolution.getEmpresaId() != null) {
                    existingCyclingWheelRevolution.setEmpresaId(cyclingWheelRevolution.getEmpresaId());
                }
                if (cyclingWheelRevolution.getRevolutions() != null) {
                    existingCyclingWheelRevolution.setRevolutions(cyclingWheelRevolution.getRevolutions());
                }
                if (cyclingWheelRevolution.getStartTime() != null) {
                    existingCyclingWheelRevolution.setStartTime(cyclingWheelRevolution.getStartTime());
                }
                if (cyclingWheelRevolution.getEndTime() != null) {
                    existingCyclingWheelRevolution.setEndTime(cyclingWheelRevolution.getEndTime());
                }

                return existingCyclingWheelRevolution;
            })
            .map(cyclingWheelRevolutionRepository::save);
    }

    /**
     * Get all the cyclingWheelRevolutions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CyclingWheelRevolution> findAll(Pageable pageable) {
        log.debug("Request to get all CyclingWheelRevolutions");
        return cyclingWheelRevolutionRepository.findAll(pageable);
    }

    /**
     * Get one cyclingWheelRevolution by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CyclingWheelRevolution> findOne(UUID id) {
        log.debug("Request to get CyclingWheelRevolution : {}", id);
        return cyclingWheelRevolutionRepository.findById(id);
    }

    /**
     * Delete the cyclingWheelRevolution by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete CyclingWheelRevolution : {}", id);
        cyclingWheelRevolutionRepository.deleteById(id);
    }
}
