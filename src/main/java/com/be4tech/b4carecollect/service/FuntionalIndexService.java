package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.FuntionalIndex;
import com.be4tech.b4carecollect.repository.FuntionalIndexRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FuntionalIndex}.
 */
@Service
@Transactional
public class FuntionalIndexService {

    private final Logger log = LoggerFactory.getLogger(FuntionalIndexService.class);

    private final FuntionalIndexRepository funtionalIndexRepository;

    public FuntionalIndexService(FuntionalIndexRepository funtionalIndexRepository) {
        this.funtionalIndexRepository = funtionalIndexRepository;
    }

    /**
     * Save a funtionalIndex.
     *
     * @param funtionalIndex the entity to save.
     * @return the persisted entity.
     */
    public FuntionalIndex save(FuntionalIndex funtionalIndex) {
        log.debug("Request to save FuntionalIndex : {}", funtionalIndex);
        return funtionalIndexRepository.save(funtionalIndex);
    }

    /**
     * Update a funtionalIndex.
     *
     * @param funtionalIndex the entity to save.
     * @return the persisted entity.
     */
    public FuntionalIndex update(FuntionalIndex funtionalIndex) {
        log.debug("Request to update FuntionalIndex : {}", funtionalIndex);
        return funtionalIndexRepository.save(funtionalIndex);
    }

    /**
     * Partially update a funtionalIndex.
     *
     * @param funtionalIndex the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FuntionalIndex> partialUpdate(FuntionalIndex funtionalIndex) {
        log.debug("Request to partially update FuntionalIndex : {}", funtionalIndex);

        return funtionalIndexRepository
            .findById(funtionalIndex.getId())
            .map(existingFuntionalIndex -> {
                if (funtionalIndex.getUsuarioId() != null) {
                    existingFuntionalIndex.setUsuarioId(funtionalIndex.getUsuarioId());
                }
                if (funtionalIndex.getEmpresaId() != null) {
                    existingFuntionalIndex.setEmpresaId(funtionalIndex.getEmpresaId());
                }
                if (funtionalIndex.getBodyHealthScore() != null) {
                    existingFuntionalIndex.setBodyHealthScore(funtionalIndex.getBodyHealthScore());
                }
                if (funtionalIndex.getMentalHealthScore() != null) {
                    existingFuntionalIndex.setMentalHealthScore(funtionalIndex.getMentalHealthScore());
                }
                if (funtionalIndex.getSleepHealthScore() != null) {
                    existingFuntionalIndex.setSleepHealthScore(funtionalIndex.getSleepHealthScore());
                }
                if (funtionalIndex.getFuntionalIndex() != null) {
                    existingFuntionalIndex.setFuntionalIndex(funtionalIndex.getFuntionalIndex());
                }
                if (funtionalIndex.getAlarmRiskScore() != null) {
                    existingFuntionalIndex.setAlarmRiskScore(funtionalIndex.getAlarmRiskScore());
                }
                if (funtionalIndex.getStartTime() != null) {
                    existingFuntionalIndex.setStartTime(funtionalIndex.getStartTime());
                }

                return existingFuntionalIndex;
            })
            .map(funtionalIndexRepository::save);
    }

    /**
     * Get all the funtionalIndices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FuntionalIndex> findAll(Pageable pageable) {
        log.debug("Request to get all FuntionalIndices");
        return funtionalIndexRepository.findAll(pageable);
    }

    /**
     * Get one funtionalIndex by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FuntionalIndex> findOne(UUID id) {
        log.debug("Request to get FuntionalIndex : {}", id);
        return funtionalIndexRepository.findById(id);
    }

    /**
     * Delete the funtionalIndex by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete FuntionalIndex : {}", id);
        funtionalIndexRepository.deleteById(id);
    }
}
