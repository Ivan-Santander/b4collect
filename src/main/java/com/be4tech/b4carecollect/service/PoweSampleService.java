package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.PoweSample;
import com.be4tech.b4carecollect.repository.PoweSampleRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PoweSample}.
 */
@Service
@Transactional
public class PoweSampleService {

    private final Logger log = LoggerFactory.getLogger(PoweSampleService.class);

    private final PoweSampleRepository poweSampleRepository;

    public PoweSampleService(PoweSampleRepository poweSampleRepository) {
        this.poweSampleRepository = poweSampleRepository;
    }

    /**
     * Save a poweSample.
     *
     * @param poweSample the entity to save.
     * @return the persisted entity.
     */
    public PoweSample save(PoweSample poweSample) {
        log.debug("Request to save PoweSample : {}", poweSample);
        return poweSampleRepository.save(poweSample);
    }

    /**
     * Update a poweSample.
     *
     * @param poweSample the entity to save.
     * @return the persisted entity.
     */
    public PoweSample update(PoweSample poweSample) {
        log.debug("Request to update PoweSample : {}", poweSample);
        return poweSampleRepository.save(poweSample);
    }

    /**
     * Partially update a poweSample.
     *
     * @param poweSample the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PoweSample> partialUpdate(PoweSample poweSample) {
        log.debug("Request to partially update PoweSample : {}", poweSample);

        return poweSampleRepository
            .findById(poweSample.getId())
            .map(existingPoweSample -> {
                if (poweSample.getUsuarioId() != null) {
                    existingPoweSample.setUsuarioId(poweSample.getUsuarioId());
                }
                if (poweSample.getEmpresaId() != null) {
                    existingPoweSample.setEmpresaId(poweSample.getEmpresaId());
                }
                if (poweSample.getVatios() != null) {
                    existingPoweSample.setVatios(poweSample.getVatios());
                }
                if (poweSample.getStartTime() != null) {
                    existingPoweSample.setStartTime(poweSample.getStartTime());
                }
                if (poweSample.getEndTime() != null) {
                    existingPoweSample.setEndTime(poweSample.getEndTime());
                }

                return existingPoweSample;
            })
            .map(poweSampleRepository::save);
    }

    /**
     * Get all the poweSamples.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PoweSample> findAll(Pageable pageable) {
        log.debug("Request to get all PoweSamples");
        return poweSampleRepository.findAll(pageable);
    }

    /**
     * Get one poweSample by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PoweSample> findOne(UUID id) {
        log.debug("Request to get PoweSample : {}", id);
        return poweSampleRepository.findById(id);
    }

    /**
     * Delete the poweSample by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete PoweSample : {}", id);
        poweSampleRepository.deleteById(id);
    }
}
