package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.CiclingPedalingCadence;
import com.be4tech.b4carecollect.repository.CiclingPedalingCadenceRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CiclingPedalingCadence}.
 */
@Service
@Transactional
public class CiclingPedalingCadenceService {

    private final Logger log = LoggerFactory.getLogger(CiclingPedalingCadenceService.class);

    private final CiclingPedalingCadenceRepository ciclingPedalingCadenceRepository;

    public CiclingPedalingCadenceService(CiclingPedalingCadenceRepository ciclingPedalingCadenceRepository) {
        this.ciclingPedalingCadenceRepository = ciclingPedalingCadenceRepository;
    }

    /**
     * Save a ciclingPedalingCadence.
     *
     * @param ciclingPedalingCadence the entity to save.
     * @return the persisted entity.
     */
    public CiclingPedalingCadence save(CiclingPedalingCadence ciclingPedalingCadence) {
        log.debug("Request to save CiclingPedalingCadence : {}", ciclingPedalingCadence);
        return ciclingPedalingCadenceRepository.save(ciclingPedalingCadence);
    }

    /**
     * Update a ciclingPedalingCadence.
     *
     * @param ciclingPedalingCadence the entity to save.
     * @return the persisted entity.
     */
    public CiclingPedalingCadence update(CiclingPedalingCadence ciclingPedalingCadence) {
        log.debug("Request to update CiclingPedalingCadence : {}", ciclingPedalingCadence);
        return ciclingPedalingCadenceRepository.save(ciclingPedalingCadence);
    }

    /**
     * Partially update a ciclingPedalingCadence.
     *
     * @param ciclingPedalingCadence the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CiclingPedalingCadence> partialUpdate(CiclingPedalingCadence ciclingPedalingCadence) {
        log.debug("Request to partially update CiclingPedalingCadence : {}", ciclingPedalingCadence);

        return ciclingPedalingCadenceRepository
            .findById(ciclingPedalingCadence.getId())
            .map(existingCiclingPedalingCadence -> {
                if (ciclingPedalingCadence.getUsuarioId() != null) {
                    existingCiclingPedalingCadence.setUsuarioId(ciclingPedalingCadence.getUsuarioId());
                }
                if (ciclingPedalingCadence.getEmpresaId() != null) {
                    existingCiclingPedalingCadence.setEmpresaId(ciclingPedalingCadence.getEmpresaId());
                }
                if (ciclingPedalingCadence.getRpm() != null) {
                    existingCiclingPedalingCadence.setRpm(ciclingPedalingCadence.getRpm());
                }
                if (ciclingPedalingCadence.getStartTime() != null) {
                    existingCiclingPedalingCadence.setStartTime(ciclingPedalingCadence.getStartTime());
                }
                if (ciclingPedalingCadence.getEndTime() != null) {
                    existingCiclingPedalingCadence.setEndTime(ciclingPedalingCadence.getEndTime());
                }

                return existingCiclingPedalingCadence;
            })
            .map(ciclingPedalingCadenceRepository::save);
    }

    /**
     * Get all the ciclingPedalingCadences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CiclingPedalingCadence> findAll(Pageable pageable) {
        log.debug("Request to get all CiclingPedalingCadences");
        return ciclingPedalingCadenceRepository.findAll(pageable);
    }

    /**
     * Get one ciclingPedalingCadence by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CiclingPedalingCadence> findOne(UUID id) {
        log.debug("Request to get CiclingPedalingCadence : {}", id);
        return ciclingPedalingCadenceRepository.findById(id);
    }

    /**
     * Delete the ciclingPedalingCadence by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete CiclingPedalingCadence : {}", id);
        ciclingPedalingCadenceRepository.deleteById(id);
    }
}
