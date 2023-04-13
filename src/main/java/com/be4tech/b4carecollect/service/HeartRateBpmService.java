package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.HeartRateBpm;
import com.be4tech.b4carecollect.repository.HeartRateBpmRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HeartRateBpm}.
 */
@Service
@Transactional
public class HeartRateBpmService {

    private final Logger log = LoggerFactory.getLogger(HeartRateBpmService.class);

    private final HeartRateBpmRepository heartRateBpmRepository;

    public HeartRateBpmService(HeartRateBpmRepository heartRateBpmRepository) {
        this.heartRateBpmRepository = heartRateBpmRepository;
    }

    /**
     * Save a heartRateBpm.
     *
     * @param heartRateBpm the entity to save.
     * @return the persisted entity.
     */
    public HeartRateBpm save(HeartRateBpm heartRateBpm) {
        log.debug("Request to save HeartRateBpm : {}", heartRateBpm);
        return heartRateBpmRepository.save(heartRateBpm);
    }

    /**
     * Update a heartRateBpm.
     *
     * @param heartRateBpm the entity to save.
     * @return the persisted entity.
     */
    public HeartRateBpm update(HeartRateBpm heartRateBpm) {
        log.debug("Request to update HeartRateBpm : {}", heartRateBpm);
        return heartRateBpmRepository.save(heartRateBpm);
    }

    /**
     * Partially update a heartRateBpm.
     *
     * @param heartRateBpm the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HeartRateBpm> partialUpdate(HeartRateBpm heartRateBpm) {
        log.debug("Request to partially update HeartRateBpm : {}", heartRateBpm);

        return heartRateBpmRepository
            .findById(heartRateBpm.getId())
            .map(existingHeartRateBpm -> {
                if (heartRateBpm.getUsuarioId() != null) {
                    existingHeartRateBpm.setUsuarioId(heartRateBpm.getUsuarioId());
                }
                if (heartRateBpm.getEmpresaId() != null) {
                    existingHeartRateBpm.setEmpresaId(heartRateBpm.getEmpresaId());
                }
                if (heartRateBpm.getPpm() != null) {
                    existingHeartRateBpm.setPpm(heartRateBpm.getPpm());
                }
                if (heartRateBpm.getEndTime() != null) {
                    existingHeartRateBpm.setEndTime(heartRateBpm.getEndTime());
                }

                return existingHeartRateBpm;
            })
            .map(heartRateBpmRepository::save);
    }

    /**
     * Get all the heartRateBpms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HeartRateBpm> findAll(Pageable pageable) {
        log.debug("Request to get all HeartRateBpms");
        return heartRateBpmRepository.findAll(pageable);
    }

    /**
     * Get one heartRateBpm by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HeartRateBpm> findOne(UUID id) {
        log.debug("Request to get HeartRateBpm : {}", id);
        return heartRateBpmRepository.findById(id);
    }

    /**
     * Delete the heartRateBpm by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete HeartRateBpm : {}", id);
        heartRateBpmRepository.deleteById(id);
    }
}
