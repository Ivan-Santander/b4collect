package com.be4tech.b4carecollect.service;

import com.be4tech.b4carecollect.domain.HeartMinutes;
import com.be4tech.b4carecollect.repository.HeartMinutesRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HeartMinutes}.
 */
@Service
@Transactional
public class HeartMinutesService {

    private final Logger log = LoggerFactory.getLogger(HeartMinutesService.class);

    private final HeartMinutesRepository heartMinutesRepository;

    public HeartMinutesService(HeartMinutesRepository heartMinutesRepository) {
        this.heartMinutesRepository = heartMinutesRepository;
    }

    /**
     * Save a heartMinutes.
     *
     * @param heartMinutes the entity to save.
     * @return the persisted entity.
     */
    public HeartMinutes save(HeartMinutes heartMinutes) {
        log.debug("Request to save HeartMinutes : {}", heartMinutes);
        return heartMinutesRepository.save(heartMinutes);
    }

    /**
     * Update a heartMinutes.
     *
     * @param heartMinutes the entity to save.
     * @return the persisted entity.
     */
    public HeartMinutes update(HeartMinutes heartMinutes) {
        log.debug("Request to update HeartMinutes : {}", heartMinutes);
        return heartMinutesRepository.save(heartMinutes);
    }

    /**
     * Partially update a heartMinutes.
     *
     * @param heartMinutes the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HeartMinutes> partialUpdate(HeartMinutes heartMinutes) {
        log.debug("Request to partially update HeartMinutes : {}", heartMinutes);

        return heartMinutesRepository
            .findById(heartMinutes.getId())
            .map(existingHeartMinutes -> {
                if (heartMinutes.getUsuarioId() != null) {
                    existingHeartMinutes.setUsuarioId(heartMinutes.getUsuarioId());
                }
                if (heartMinutes.getEmpresaId() != null) {
                    existingHeartMinutes.setEmpresaId(heartMinutes.getEmpresaId());
                }
                if (heartMinutes.getSeverity() != null) {
                    existingHeartMinutes.setSeverity(heartMinutes.getSeverity());
                }
                if (heartMinutes.getStartTime() != null) {
                    existingHeartMinutes.setStartTime(heartMinutes.getStartTime());
                }
                if (heartMinutes.getEndTime() != null) {
                    existingHeartMinutes.setEndTime(heartMinutes.getEndTime());
                }

                return existingHeartMinutes;
            })
            .map(heartMinutesRepository::save);
    }

    /**
     * Get all the heartMinutes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HeartMinutes> findAll(Pageable pageable) {
        log.debug("Request to get all HeartMinutes");
        return heartMinutesRepository.findAll(pageable);
    }

    /**
     * Get one heartMinutes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HeartMinutes> findOne(UUID id) {
        log.debug("Request to get HeartMinutes : {}", id);
        return heartMinutesRepository.findById(id);
    }

    /**
     * Delete the heartMinutes by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete HeartMinutes : {}", id);
        heartMinutesRepository.deleteById(id);
    }
}
